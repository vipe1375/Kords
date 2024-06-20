package com.example.kordsjetpack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChordsViewModel : ViewModel() {

    var chordsList = readJson()

    var currentChord by mutableStateOf("0232")
        private set

    var currentChordName by mutableStateOf("G")
        private set

    var searched by mutableStateOf(value = false)
        private set

    var chordSearched by mutableStateOf("")
        private set

    var chordSearchedID by mutableStateOf("")
        private set

    var textState by mutableStateOf(value = "")
        private set

    var chordFound by mutableStateOf(value = false)
        private set

    var showSuggestions by mutableStateOf(value = true)
        private set

    fun changeCurrentChord(newChord: String) {
        /**
         * update currentChord ID to the given ID
         * @param newChord: String ("0232")
         */
        currentChord = newChord
        currentChordName = if (getChordByID(currentChord).isEmpty()) {
            "no chord found"
        } else {
            getChordByID(currentChord)
        }
    }

    fun changeChordSearched(newChord: String) {
        chordSearched = newChord
        searched = false
    }

    private fun resetChordSearched() {
        chordSearched = ""
    }

    fun searchChord() {
        chordFound = false
        val chord = getChordByName(chordSearched.lowercase())
        if (chord.isEmpty()) {
            textState = "No chord found with name $chordSearched"
        } else {
            textState = "Chord found, id : $chord"
            chordFound = true
            chordSearchedID = chord
        }

        searched = true
    }

    fun visualizeChord(chordID: String) {
        searched = false
        changeCurrentChord(chordID)
        resetChordSearched()
    }
}