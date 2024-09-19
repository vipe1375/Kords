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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song
import com.vipedev.kords.songs_screen.database.SongsDao
import kotlinx.coroutines.launch

class SongsViewModel (
    private val dao: SongsDao,
    private val context: Context
) : ViewModel() {

    // creating song
    var isEditingSong by mutableStateOf(false)

    var currentSong: Song? by mutableStateOf(null)

    var titleField by mutableStateOf("")

    var artistField by mutableStateOf("")

    val structTypes: List<String> = mutableListOf(
        context.getString(R.string.section_intro),
        context.getString(R.string.section_chorus),
        context.getString(R.string.section_verse),
        context.getString(R.string.section_solo),
        context.getString(R.string.section_outro),
        context.getString(R.string.section_bridge))

    var currentStructType by mutableStateOf("")

    var currentChords by mutableStateOf("")

    var structDropdownState by mutableStateOf(false)

    var struct : MutableMap<String, String> = mutableMapOf()

    var songs : LiveData<List<Song>> = dao.getSongs_Artist()

    private var duplicableStructTypes = mutableMapOf(
        context.getString(R.string.section_chorus) to 1,
        context.getString(R.string.section_verse) to 1,
        context.getString(R.string.section_bridge) to 1,
        context.getString(R.string.section_solo) to 1)

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
        currentStructType = value
    }

    fun updateStructDropdownState(value: Boolean) {
        structDropdownState = value
    }

    fun addStructItem() {

        if (currentStructType in duplicableStructTypes.keys) {
            // if the struct type is duplicable, adds a number after the struct type name
            // ex : chorus -> chorus 1, 2...
            struct["$currentStructType ${duplicableStructTypes[currentStructType]}"] = currentChords

            // updating the number of the struct type
            duplicableStructTypes[currentStructType] = duplicableStructTypes[currentStructType]!! + 1
        }
        else {
            struct[currentStructType] = currentChords
        }
        currentChords = ""
        currentStructType = ""
    }

    fun updateCurrentChords(value: String) {
        currentChords = value
    }

    private fun resetStructElement() {
        currentChords = ""
        currentStructType = ""
    }

    fun displayToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
        duplicableStructTypes = mutableMapOf(
            context.getString(R.string.section_chorus) to 1,
            context.getString(R.string.section_verse) to 1,
            context.getString(R.string.section_bridge) to 1,
            context.getString(R.string.section_solo) to 1)
    }

    fun resetCurrentSong() {
        currentSong = null
    }

    fun initCurrentSong(song: Song) {
        currentSong = song
        artistField = song.artist
        titleField = song.title

        struct = convertDBSong(song.structure)
    }

    private fun convertDBSong(struct: Map<String, List<String>>) : MutableMap<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()
        struct.forEach { (section, chords) ->
            result[section] = chords.joinToString(separator = " ")
        }

        return result
    }

}