package com.vipedev.kords.chords.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vipedev.kords.R
import com.vipedev.kords.chords.database.Chord
import com.vipedev.kords.chords.database.ChordsDao
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
class ChordsViewModel(
    private val chordsDao: ChordsDao,
    private val context: Context
) : ViewModel() {

    var currentChord by mutableStateOf(mutableListOf("0", "2", "3", "2")) // id of the current chord
        private set

    var currentChordName by mutableStateOf("G") // name of the current chord
        private set

    var searched by mutableStateOf(value = false) // if user clicked search
        private set

    var chordSearched by mutableStateOf("") // chord typed by the user
        private set

    var chordSearchedID by mutableStateOf(
        mutableListOf(
            "",
            "",
            "",
            ""
        )
    ) // id of the chord searched (if found)
        private set

    var textState by mutableStateOf(value = "") // value of text
        private set

    var searchResult by mutableStateOf(mutableListOf(Chord())) // list of chords found by name
        private set

    var visualizedID by mutableIntStateOf(1) // id of the currently visualized chord

    var showVisualizeButton by mutableStateOf(false) // show visualized button or not when a chord is searched

    var showSuggestions by mutableStateOf(false)

    fun changeCurrentChord(newChord: MutableList<String>) {
        /**
         * update currentChord ID to the given ID
         * @param newChord
         */
        searched = false
        currentChord = newChord

        val dbResult = chordsDao.getChordsByFingers(currentChord.joinToString("-"))

        if (dbResult.isEmpty()) {
            currentChordName = context.getString(R.string.no_chord_found_id)

        } else {
            var temp = dbResult[0].name
            for (i in 1 until dbResult.size) {
                temp += " / ${dbResult[i].name}"
            }
            currentChordName = temp
        }

    }

    fun changeChordSearched(newChord: String) {
        chordSearched = newChord
        searched = false
    }

    suspend fun delaySuggestions() {
        showSuggestions = false
        delay(500)
        showSuggestions = true
    }

    fun resetChordSearched() {
        chordSearched = ""
    }

    private fun resetSearchResult() {
        searchResult = mutableListOf(Chord())
    }

    fun searchChord() {
        val result = chordsDao.getChordsByName(chordSearched.lowercase())

        if (result.isNotEmpty()) {
            searchResult = result.toMutableList()
            textState = context.resources.getQuantityString(
                R.plurals.chord_found_name,
                result.size,
                result.size,
                chordSearched
            )
            chordSearchedID = result[0].fingers.split("-").toMutableList()
            showVisualizeButton = true

        } else {
            textState = context.getString(R.string.no_chord_found_name, chordSearched)
        }

        searched = true
    }

    fun getSuggestions(): List<Chord> {
        return chordsDao.getSuggestions(chordSearched)
    }

    fun visualizeChord(chord: Chord) {

        currentChord = chord.fingers.split("-").toMutableList()
        currentChordName = chord.name


        if (searchResult.size == 1) {
            showVisualizeButton = false
            searched = false
            resetChordSearched()
            resetSearchResult()
        }


    }

    fun changeVisualizedChord(right: Boolean) {

        if (right) {
            visualizedID += 1
            if (visualizedID > searchResult.size) {
                visualizedID = 1
            }
        } else {
            visualizedID -= 1
            if (visualizedID < 1) {
                visualizedID = searchResult.size
            }
        }
    }

}