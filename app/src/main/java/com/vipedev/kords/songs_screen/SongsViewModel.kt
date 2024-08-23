package com.vipedev.kords.songs_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vipedev.kords.songs_screen.database.Song
import com.vipedev.kords.songs_screen.database.SongsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SongsViewModel (
    private val dao: SongsDao,
) : ViewModel() {

    var isCreatingSong by mutableStateOf(false)

    var titleField by mutableStateOf("")

    var artistField by mutableStateOf("")

    val structTypes: List<String> = mutableListOf("Intro", "Chorus", "Verse", "Solo", "Outro", "Bridge")

    var currentStructType by mutableStateOf("")

    var currentChords by mutableStateOf("")

    var structDropdownState by mutableStateOf(false)

    var struct : MutableMap<String, String> = mutableMapOf()

    var songs : List<Song> = mutableListOf()

    fun updateIsCreatingSong(value: Boolean) {
        isCreatingSong = value
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

    fun addStructItem(type: String, chords: String) {
        struct[type] = chords
    }

    fun updateCurrentChords(value: String) {
        currentChords = value
    }

    fun resetStructElement() {
        currentChords = ""
        currentStructType = ""
        struct = mutableMapOf()
    }

    private fun displayToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    suspend fun saveSong(title: String, artist: String, structure: Map<String, String>, context: Context) {

        if (title.isNotEmpty() && artist.isNotEmpty() && structure.isNotEmpty()) {
            // reformatting the chords
            val formattedStruct: MutableMap<String, List<String>> = mutableMapOf()

            structure.forEach { (type, chords) ->
                formattedStruct[type] = chords.split(" ")
            }

            val song = Song(title = title, artist = artist, structure = formattedStruct)
            dao.upsertSong(song)
            resetCreation()
            updateIsCreatingSong(false)
        } else {
            displayToast(context, "Missing informations !")
        }

    }

    fun deleteSong(song: Song, context: Context) {
        viewModelScope.launch {
            dao.deleteSong(song)
        }
        updateSongs()
        displayToast(context = context, text = "Song deleted !")
    }




    fun getSavedSongs() : List<Song> {
        updateSongs()
        return songs
    }

    private fun updateSongs() {
        viewModelScope.launch {
            dao.getSongs_Title().collect {
                songs = it
            }
        }
    }


    fun resetCreation() {
        resetStructElement()
        titleField = ""
        artistField = ""
    }

}