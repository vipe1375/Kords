package com.vipedev.kords.chords

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vipedev.kords.ChordPlayer
import com.vipedev.kords.R
import com.vipedev.kords.Synth
import com.vipedev.kords.chords.database.Chord
import com.vipedev.kords.chords.database.allChords
import com.vipedev.kords.chords.database.findChord
import com.vipedev.kords.chords.database.nameChord
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

class ChordsViewModel(
    private val context: Context,
    val player: ChordPlayer,
) : ViewModel() {

    private val synth = Synth()

    override fun onCleared() {
        synth.release()   // nécessite la méthode native release()
    }

    // --- Chord displayed on the grid ---
    var currentChord by mutableStateOf(Chord("G", "0-2-3-2"))
        private set

    var alternativeChords by mutableStateOf<List<Chord>>(emptyList())
        private set

    // --- Search ---
    var textInput by mutableStateOf("G")
        private set

    var showSuggestions by mutableStateOf(false)

    var searchResult by mutableStateOf<List<Chord>>(emptyList())
        private set

    var showVisualizeButton by mutableStateOf(false)
        private set

    var visualizedIndex by mutableIntStateOf(0) // index of the chord in searchResult

    var isScreenVisible by mutableStateOf(true)

    var playSlowChord by mutableStateOf(false)

    init {
        searchChord()

        synth.init()
        val sf2 = File(context.filesDir, "ukulele.sf2")
        if (!sf2.exists())
            context.assets.open("ukulele.sf2").use { i -> sf2.outputStream().use { i.copyTo(it) } }


        val id = synth.loadSf2(sf2.absolutePath)
        Log.d("Synth", "sfload id=$id, exists=${sf2.exists()}, size=${sf2.length()}")
    }

    /**
     * Updates the displayed chord based on a fingering string.
     *
     * It identifies the possible names for the given fingering, selecting the shortest name
     * as the primary title and storing the others as alternative names.
     *
     * @param fingers A string representing the chord fingering (e.g., "0-2-3-2").
     */
    private fun displayFingering(fingers: String) {

        val names = nameChord(fingers)

        if (names.isEmpty()) {
            currentChord = Chord(
                name = context.getString(R.string.no_chord_found_name),
                fingers = fingers
            )
            return
        }
        currentChord = names[0]

        alternativeChords = names.drop(1)
    }

    /**
     * Changes the displayed chord and erases search result.
     * Called when a button is clicked on the grid.
     * @param fingers A list of strings representing the chord fingering (e.g., ["0", "2", "3", "2"]).
     * */
    fun changeFingering(fingers: List<Int>) {
        displayFingering(fingers.joinToString("-"))
        searchResult = emptyList()
        showVisualizeButton = false
        playSlowChord = false
    }

    fun changeTextInput(newText: String) {
        textInput = newText.trim()
    }

    /**
     * Searches a chord by its name.
     * Called when clicking on the search button or a suggestion in the dropdown.*/
    fun searchChord() {
        showSuggestions = false
        val result = findChord(textInput)

        if (result.isNotEmpty()) {
            searchResult = result
            visualizedIndex = 0
            showVisualizeButton = true
            displayFingering(result[0].fingers)
        } else {
            // No chord found
            currentChord = Chord(
                context.getString(R.string.no_chord_found_name),
                currentChord.fingers
            )
            alternativeChords = emptyList()
            searchResult = emptyList()
            showVisualizeButton = false
        }
    }

    /** Changes the displayed chord to the next one in the search result.
     * Called when clicking the View button */
    fun changeVisualizedChord() {
        if (searchResult.isEmpty()) return
        visualizedIndex = (visualizedIndex + 1) % searchResult.size
        displayFingering(searchResult[visualizedIndex].fingers)
    }

    fun getSuggestions(): List<String> {
        val query = textInput.lowercase()
        if (query.isEmpty()) return emptyList()
        return allChords
            .filter { it.contains(query.lowercase()) }
            .sortedBy { it.length }
    }

    suspend fun delaySuggestions() {
        showSuggestions = false
        delay(500.milliseconds)
        showSuggestions = true
    }

    fun resetChordSearched() {
        textInput = ""
    }

    fun playChord(chord: Chord) {
        player.playChord(chord)
    }
}