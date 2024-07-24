package com.vipedev.kords.chordscreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vipedev.kords.database.Chord
import com.vipedev.kords.database.ChordsDao
import com.vipedev.kords.R

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

    var chordFound by mutableStateOf(value = false) // if searched chord was found
        private set

    fun changeCurrentChord(newChord: MutableList<String>) {
        /**
         * update currentChord ID to the given ID
         * @param newChord: MutableList<String>
         */
        currentChord = newChord
        println(currentChord)

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

    private fun resetChordSearched() {
        chordSearched = ""
    }

    fun searchChord() {
        chordFound = false
        val result = chordsDao.getChordsByName(chordSearched.lowercase())

        if (result.isNotEmpty()) {
            textState = context.resources.getQuantityString(
                R.plurals.chord_found_name,
                result.size,
                result.size,
                chordSearched
            )
            chordFound = true
            chordSearchedID = result[0].fingers.split("-").toMutableList()

        } else {
            textState = context.getString(R.string.no_chord_found_name, chordSearched)
        }

        searched = true
    }

    fun getSuggestions(): List<Chord> {
        return chordsDao.getSuggestions(chordSearched)
    }

    fun visualizeChord(chordID: MutableList<String>) {
        searched = false
        changeCurrentChord(chordID)
        resetChordSearched()
    }

}