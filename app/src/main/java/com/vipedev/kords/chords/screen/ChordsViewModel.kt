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
import com.vipedev.kords.chords.database.allChords
import com.vipedev.kords.chords.database.findChord
import com.vipedev.kords.chords.database.findChord2
import com.vipedev.kords.chords.database.nameChord
import kotlinx.coroutines.delay

@SuppressLint("MutableCollectionMutableState")
class ChordsViewModel(
    private val context: Context
) : ViewModel() {

    var currentChord by mutableStateOf(mutableListOf("0", "2", "3", "2")) // id of the current chord
        private set

    var currentChordName by mutableStateOf("G") // name of the current chord
        private set

    var searched by mutableStateOf(value = false) // if user clicked search
        private set

    var chordSearched by mutableStateOf("G") // chord typed by the user
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

    var searchResult: MutableList<Chord> by mutableStateOf(mutableListOf()) // list of chords found by name
        private set

    var visualizedID by mutableIntStateOf(1) // id of the currently visualized chord

    var showVisualizeButton by mutableStateOf(false) // show visualized button or not when a chord is searched

    var showSuggestions by mutableStateOf(false)

    var extraChordNames by mutableStateOf("") // To store "Em7-G" etc.
        private set

    init {
        searchChord()
    }

    fun changeCurrentChord(newChord: MutableList<String>) {
        /**
         * update currentChord ID to the given ID
         * @param newChord
         */
        searched = false
        currentChord = newChord

        val names = nameChord(context, currentChord.joinToString(separator = "-"))
        val namesList = names.split("-").sortedBy { it.length }
        currentChordName = namesList[0]
        extraChordNames = if (namesList.size > 1) {
            namesList.drop(1).joinToString(separator = "-")
        } else {
            ""
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

    fun searchChord() {
        showVisualizeButton = false
        visualizedID = 1
        val result = findChord2(chordSearched.lowercase())
        println(result)
        //val result = chordsDao.getChordsByName(chordSearched.lowercase())

        if (result.isNotEmpty()) {
            searchResult = result.toMutableList()
            chordSearchedID = result[0].fingers.split("-").toMutableList()
            showVisualizeButton = true
            visualizeChord(result[0])
            // changeCurrentChord(chordSearchedID)
            currentChordName = result[0].name

        } else {
            currentChordName = context.getString(R.string.no_chord_found_name)
            showVisualizeButton = false
            extraChordNames = ""
        }

        searched = true
    }

    fun getSuggestions(): List<String> {
        val query = chordSearched.lowercase()
        if (query.isEmpty()) return emptyList()

        return allChords
            .filter { it.lowercase().contains(query) }
            .sortedBy { it.length }
    }

    fun visualizeChord(chord: Chord) {
        currentChord = chord.fingers.split("-").toMutableList()
        currentChordName = chord.name

        val allNames = nameChord(context, chord.fingers)

        if (allNames == currentChordName) {
            extraChordNames = ""
        }
        else {
            val extraNames = allNames.split("-").toMutableList()
            println(extraNames)
            extraNames.remove(currentChordName)
            println(extraNames)
            println("DEBUG : ${extraNames.joinToString(separator = "-")}")
            extraChordNames = extraNames.joinToString(separator = "-")
        }
    }

    fun changeVisualizedChord() {
        visualizedID += 1
        if (visualizedID > searchResult.size) {
            visualizedID = 1
        }

        visualizeChord(searchResult[visualizedID - 1])
    }

}