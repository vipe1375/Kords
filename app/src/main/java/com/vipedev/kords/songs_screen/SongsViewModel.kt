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

    var isCreatingSong by mutableStateOf(false)

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

    private val duplicableStructTypes = mutableMapOf(
        context.getString(R.string.section_chorus) to 1,
        context.getString(R.string.section_verse) to 1,
        context.getString(R.string.section_bridge) to 1,
        context.getString(R.string.section_solo) to 1)

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
    }

}