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

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song
import com.vipedev.kords.songs_screen.database.SongsDao
import kotlinx.coroutines.launch

class SongsViewModel (
    private val dao: SongsDao,
    private val context: Context
) : ViewModel() {

    // SONG CREATION & EDITION
    var isEditingSong by mutableStateOf(false) // is the user editing a song

    var currentSong: Song? by mutableStateOf(null) // the song displayed or edited

    var titleField by mutableStateOf("") // value of "Title" field while editing

    var artistField by mutableStateOf("") // value of "Artist" field while editing

    val sectionTypes: List<String> = mutableListOf( // possible section types
        context.getString(R.string.section_intro),
        context.getString(R.string.section_chorus),
        context.getString(R.string.section_verse),
        context.getString(R.string.section_solo),
        context.getString(R.string.section_outro),
        context.getString(R.string.section_bridge))

    private var duplicableSectionTypes = mutableMapOf( // section types that are duplicable
        "Chorus" to 1,
        "Verse" to 1,
        "Bridge" to 1,
        "Solo" to 1)

    var currentSection by mutableStateOf("") // section type being edited

    var currentChords by mutableStateOf("") // chords of the current section

    var sectionDropdownState by mutableStateOf(false) // state of the Dropdown menu to create a new section

    var struct : MutableMap<String, String> = mutableMapOf() // structure of the current song

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
            // if the struct type is duplicable, adds a number after the struct type name
            // ex : chorus -> chorus 1, 2...
            struct["$currentSection ${duplicableSectionTypes[currentSection]}"] = currentChords

            // updating the number of the struct type
            duplicableSectionTypes[currentSection] = duplicableSectionTypes[currentSection]!! + 1
        }
        else {
            // not duplicable section type, so no need for a number after the section name
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

            // reformatting the chords
            val formattedStruct: MutableMap<String, List<String>> = mutableMapOf()

            structure.forEach { (type, chords) ->
                formattedStruct[type] = chords.split(" ")
            }

            // if editing a song
            if (song == null) {
                println("insert")
                val newSong = Song(title = title, artist = artist, structure = formattedStruct)
                dao.insertSong(newSong)
            }
            else {
                println("update")
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
        //updateSongs()
        displayToast(context = context, text = context.getString(R.string.song_deleted))
    }

    fun resetCreation() {
        resetStructElement()
        struct = mutableMapOf()
        titleField = ""
        artistField = ""
        duplicableSectionTypes = mutableMapOf(
            "Chorus" to 1,
            "Verse" to 1,
            "Bridge" to 1,
            "Solo" to 1)
    }

    fun resetCurrentSong() {
        currentSong = null
    }

    private fun initCurrentSong(song: Song) {
        currentSong = song
        artistField = song.artist
        titleField = song.title

        struct = convertDBSong(song.structure)

        // updating the number of duplicable structures elements
        println(duplicableSectionTypes)
        struct.keys.forEach { structElt ->
            // split structElt to get only the type of element, not the number
            val sectionType = structElt.split(" ")[0]
            if (sectionType in duplicableSectionTypes) {
                duplicableSectionTypes[sectionType] = duplicableSectionTypes[sectionType]!! + 1
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