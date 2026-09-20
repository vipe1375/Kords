/*
 * Kords
 * Copyright (C) 2024 Victor Pezennec--Deutsch
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.vipedev.kords.songs

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vipedev.kords.ChordPlayer
import com.vipedev.kords.R
import com.vipedev.kords.chords.database.Chord
import com.vipedev.kords.songs.database.Song
import com.vipedev.kords.songs.database.SongsDao
import com.vipedev.kords.songs.database.importSongFromTxt
import kotlinx.coroutines.launch

class SongsViewModel (
    private val dao: SongsDao,
    application: Application,
    val player: ChordPlayer,
) : AndroidViewModel(application) {

    var isScreenVisible by mutableStateOf(false) // used for transition animations

    private val context: Context get() = getApplication<Application>().applicationContext
    val sectionTypes = listOf("Intro", "Couplet", "Refrain", "Solo", "Outro", "Pont") // stored in French, translated in UI
    private val unnumberedTypes = setOf("Intro", "Outro") // every other type is numbered: "Couplet 1", "Couplet 2"...


    // SONG CREATION & EDITION
    var isEditingSong by mutableStateOf(false)
    var currentSong: Song? by mutableStateOf(null)
    val isNewSong: Boolean get() = currentSong?.id == null // true for a new song and for an imported one (not inserted yet)
    var titleField by mutableStateOf("")
    var artistField by mutableStateOf("")
    var currentSection by mutableStateOf("") // section being created (stable key, e.g. "Refrain")
    var currentChords by mutableStateOf("")
    // sections in the order they were added (a new map is built on each change, see updateStruct)
    var struct by mutableStateOf<Map<String, String>>(emptyMap(), neverEqualPolicy())
        private set

    // CHORD DIALOG (when a chord is clicked)
    var showChordDialog by mutableStateOf(false)
    var chordDialogResultId by mutableIntStateOf(0)
    var chordInDialogName by mutableStateOf("")

    // SONGS LIST
    private val _sortingType = mutableIntStateOf(0)
    val sortingType: State<Int> get() = _sortingType
    private val _sortAsc = mutableStateOf(true)
    val sortAsc: State<Boolean> get() = _sortAsc
    private val allSongs: LiveData<List<Song>> = dao.getAllSongs()
    val songs = MediatorLiveData<List<Song>>().apply {
        fun update() {
            val list = allSongs.value ?: return
            val sorted = when (sortingType.value) {
                0 -> if (sortAsc.value) list.sortedBy { it.artist.lowercase() } else list.sortedByDescending { it.artist.lowercase() }
                1 -> if (sortAsc.value) list.sortedBy { it.title.lowercase() } else list.sortedByDescending { it.title.lowercase() }
                2 -> if (sortAsc.value) list.sortedBy { it.id } else list.sortedByDescending { it.id }
                else -> list
            }
            value = sorted
        }
        val sortAscLive = snapshotFlow { _sortAsc.value }.asLiveData()
        val sortingTypeLive = snapshotFlow { _sortingType.intValue }.asLiveData()
        addSource(allSongs) { update() }
        addSource(sortingTypeLive) { update() }
        addSource(sortAscLive) { update() }
    }

    // DELETE DIALOG
    var showDeleteSongDialog by mutableStateOf(false)
    var showDeleteSectionDialog by mutableStateOf(false)
    var songToDelete: Song? = null
    var sectionToDelete: String? = null
    var chordsToDelete: String? = null


    // ---------------------------------------------------------------
    // EDITION FORM: OPEN / CLOSE
    // ---------------------------------------------------------------

    /** "Create a song" button. */
    fun startNewSong() {
        resetCreation()
        isEditingSong = true
    }

    /** Edit an existing song, or an imported one (id == null: it is inserted on save). */
    fun initEdition(song: Song) {
        currentSong = song
        fillForm(song)
        isEditingSong = true
    }

    /** Back button of the edition screen. */
    fun cancelEditing() {
        isEditingSong = false
        if (isNewSong) {
            resetCreation()
            resetCurrentSong()
        }
    }

    /** Empties the form, without touching currentSong. */
    fun resetCreation() = fillForm(null)

    fun resetCurrentSong() {
        currentSong = null
    }

    private fun fillForm(song: Song?) {
        titleField = song?.title ?: ""
        artistField = song?.artist ?: ""
        struct = song?.let { convertDBSong(it.structure) } ?: emptyMap()
        currentSection = ""
        currentChords = ""
    }

    private fun convertDBSong(struct: Map<String, List<String>>) : MutableMap<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()
        struct.forEach { (section, chords) ->
            result[section] = chords.joinToString(separator = " ")
        }
        return result
    }


    // ---------------------------------------------------------------
    // STRUCTURE (sections)
    // ---------------------------------------------------------------

    private fun updateStruct(block: MutableMap<String, String>.() -> Unit) {
        struct = struct.toMutableMap().apply(block) // copy that keeps the insertion order
    }

    /** Adds the section being created (currentSection / currentChords) to the structure. */
    fun addStructItem(): Boolean {
        if (currentChords.isBlank()) {
            displayToast(context, context.getString(R.string.create_song_no_chords))
            return false
        }
        val name = nextSectionName(currentSection)
        updateStruct { this[name] = currentChords }
        currentSection = ""
        currentChords = ""
        return true
    }

    fun updateSection(name: String, chords: String) = updateStruct { this[name] = chords }

    /** Removes a section & updates same type section's number */
    fun removeSection(name: String) {
        val type = name.substringBefore(" ")
        val removedNumber = name.substringAfter(" ", "1").toIntOrNull()
        val isNumbered = type !in unnumberedTypes && removedNumber != null

        struct = struct.entries
            .filter { it.key != name }
            .associate { (key, chords) ->
                val number = key.substringAfter(" ", "").toIntOrNull()
                val newKey =
                    if (isNumbered && key.substringBefore(" ") == type &&
                        number != null && number > removedNumber!!
                    ) "$type ${number - 1}"
                    else key
                newKey to chords
            }
    }

    /** Opens the confirmation dialog (see DeleteDialog). */
    fun askDeleteSection(name: String) {
        sectionToDelete = name
        chordsToDelete = struct[name]
        showDeleteSectionDialog = true
    }

    /** "Couplet" -> "Couplet 1", then "Couplet 2"... ; "Intro" and "Outro" are never numbered. */
    private fun nextSectionName(type: String): String {
        if (type in unnumberedTypes) return type
        val last = struct.keys
            .filter { it.substringBefore(" ") == type }
            .maxOfOrNull { it.substringAfter(" ", "1").toIntOrNull() ?: 1 } ?: 0
        return "$type ${last + 1}"
    }


    // ---------------------------------------------------------------
    // SAVE / DELETE / IMPORT
    // ---------------------------------------------------------------

    /** Saves the form: inserts a new (or imported) song, updates an existing one. */
    fun saveSong() {
        val sections = struct.filter { (name, chords) -> name.isNotBlank() && chords.isNotBlank() }

        if (titleField.isEmpty() || artistField.isEmpty() || sections.isEmpty()) {
            displayToast(context, context.getString(R.string.create_song_missing_informations))
            return
        }

        val song = Song(
            title = titleField,
            artist = artistField,
            structure = sections.mapValues { (_, chords) -> chords.split(" ").filter { it.isNotBlank() } },
            id = currentSong?.id
        )

        viewModelScope.launch {
            if (song.id == null) {
                dao.insertSong(song)
                resetCurrentSong() // back to the songs list
            } else {
                dao.updateSong(song)
                currentSong = song // back to the (updated) song
            }
            resetCreation()
            isEditingSong = false
        }
    }

    fun deleteSong(song: Song, context: Context) {
        viewModelScope.launch {
            dao.deleteSong(song)
        }
        displayToast(context = context, text = context.getString(R.string.song_deleted))
    }

    fun importSongFromUri(uri: Uri) {
        viewModelScope.launch {
            importSongFromTxt(context, uri)?.let { initEdition(it) }
        }
    }


    // ---------------------------------------------------------------
    // OTHERS
    // ---------------------------------------------------------------

    /**
     * Translates a section key (internal or legacy) to a localized display name.
     */
    fun getLocalizedSectionName(section: String): String {
        val sectionSplit = section.split(" ")
        val sectionKey = sectionSplit[0]
        val number = if (sectionSplit.size > 1) " ${sectionSplit[1]}" else ""

        val resId = when(sectionKey) {
            "Refrain", "Chorus" -> R.string.section_chorus
            "Couplet", "Verse" -> R.string.section_verse
            "Pont", "Bridge" -> R.string.section_bridge
            "Solo" -> R.string.section_solo
            "Intro" -> R.string.section_intro
            "Outro" -> R.string.section_outro
            else -> null
        }

        return if (resId != null) context.getString(resId) + number else section
    }

    fun updateSortingType() {
        _sortingType.intValue = (_sortingType.intValue + 1) % 3
    }

    fun updateSortingOrder() {
        _sortAsc.value = !_sortAsc.value
    }

    private fun displayToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun playChord(chord: Chord) {
        player.playChord(chord)
    }
}
