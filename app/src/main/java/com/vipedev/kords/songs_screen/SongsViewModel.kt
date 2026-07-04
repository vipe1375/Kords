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

package com.vipedev.kords.songs_screen

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song
import com.vipedev.kords.songs_screen.database.SongsDao
import kotlinx.coroutines.launch

class SongsViewModel (
    private val dao: SongsDao,
    application: Application
) : AndroidViewModel(application) {

    private val context: Context get() = getApplication<Application>().applicationContext

    // SONG CREATION & EDITION
    var isEditingSong by mutableStateOf(false)

    var currentSong: Song? by mutableStateOf(null)

    var titleField by mutableStateOf("")

    var artistField by mutableStateOf("")

    // Use stable internal keys for logic and storage
    val sectionTypes = listOf("Intro", "Couplet", "Refrain", "Solo", "Outro", "Pont")

    private var duplicableSectionTypes = mutableMapOf(
        "Couplet" to 1,
        "Refrain" to 1,
        "Pont" to 1,
        "Solo" to 1)

    var currentSection by mutableStateOf("") // Holds the stable key (e.g. "Chorus")

    var currentChords by mutableStateOf("")

    var sectionDropdownState by mutableStateOf(false)

    val struct = mutableStateMapOf<String, String>()

    fun updateIsEditingSong(value: Boolean) {
        isEditingSong = value
    }

    fun updateTitleField(value: String) {
        titleField = value
    }

    fun updateArtistField(value: String) {
        artistField = value
    }

    fun updateCurrentStructType(value: String) {
        currentSection = value
    }

    fun updateStructDropdownState(value: Boolean) {
        sectionDropdownState = value
    }

    fun initEdition(song: Song) {
        resetCreation()
        initCurrentSong(song)
        updateIsEditingSong(true)
    }

    fun addStructItem() {
        if (currentSection in duplicableSectionTypes.keys) {
            val count = duplicableSectionTypes[currentSection] ?: 1
            struct["$currentSection $count"] = currentChords
            duplicableSectionTypes[currentSection] = count + 1
        }
        else {
            struct[currentSection] = currentChords
        }
        currentChords = ""
        currentSection = ""
    }

    fun updateCurrentChords(value: String) {
        currentChords = value
    }

    private fun resetStructElement() {
        currentChords = ""
        currentSection = ""
    }

    suspend fun saveSong(title: String, artist: String, structure: Map<String, String>, context: Context, song: Song? = null) {
        if (title.isNotEmpty() && artist.isNotEmpty() && structure.isNotEmpty()) {
            val formattedStruct: MutableMap<String, List<String>> = mutableMapOf()
            structure.forEach { (type, chords) ->
                formattedStruct[type] = chords.split(" ").filter { it.isNotBlank() }
            }

            if (song == null) {
                val newSong = Song(title = title, artist = artist, structure = formattedStruct)
                dao.insertSong(newSong)
            }
            else {
                val newSong = Song(title = title, artist = artist, structure = formattedStruct, id = song.id)
                dao.updateSong(newSong)
                currentSong = newSong
            }

            resetCreation()
            updateIsEditingSong(false)
        } else {
            displayToast(context, context.getString(R.string.create_song_missing_informations))
        }
    }

    fun deleteSong(song: Song, context: Context) {
        viewModelScope.launch {
            dao.deleteSong(song)
        }
        displayToast(context = context, text = context.getString(R.string.song_deleted))
    }

    fun resetCreation() {
        resetStructElement()
        struct.clear()
        titleField = ""
        artistField = ""
        duplicableSectionTypes = mutableMapOf(
            "Refrain" to 1,
            "Couplet" to 1,
            "Pont" to 1,
            "Solo" to 1)
    }

    fun resetCurrentSong() {
        currentSong = null
    }

    private fun initCurrentSong(song: Song) {
        currentSong = song
        artistField = song.artist
        titleField = song.title
        struct.clear()
        struct.putAll(convertDBSong(song.structure))

        // Reset duplicable counters
        duplicableSectionTypes = mutableMapOf(
            "Refrain" to 1,
            "Couplet" to 1,
            "Pont" to 1,
            "Solo" to 1)

        struct.keys.forEach { structElt ->
            val parts = structElt.split(" ")
            val sectionType = parts[0]
            if (sectionType in duplicableSectionTypes) {
                val number = parts.getOrNull(1)?.toIntOrNull() ?: 1
                if (number >= duplicableSectionTypes[sectionType]!!) {
                    duplicableSectionTypes[sectionType] = number + 1
                }
            }
        }
    }

    private fun convertDBSong(struct: Map<String, List<String>>) : MutableMap<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()
        struct.forEach { (section, chords) ->
            result[section] = chords.joinToString(separator = " ")
        }
        return result
    }

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

    // SONGS LIST
    private val _sortingType = mutableIntStateOf(0)
    val sortingType: State<Int> get() = _sortingType
    fun updateSortingType() {
        _sortingType.intValue = (_sortingType.intValue + 1) % 3
    }

    private val _sortAsc = mutableStateOf(true)
    val sortAsc: State<Boolean> get() = _sortAsc
    fun updateSortingOrder() {
        _sortAsc.value = !_sortAsc.value
    }

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
    
    private fun displayToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
