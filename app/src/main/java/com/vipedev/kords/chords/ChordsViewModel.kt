package com.vipedev.kords.chords

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vipedev.kords.R
import com.vipedev.kords.Synth
import com.vipedev.kords.chords.database.Chord
import com.vipedev.kords.chords.database.allChords
import com.vipedev.kords.chords.database.findChord2
import com.vipedev.kords.chords.database.nameChord
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

class ChordsViewModel(
    private val context: Context
) : ViewModel() {

    private val synth = Synth()

    override fun onCleared() {
        synth.release()   // nécessite la méthode native release()
    }

    // --- Chord displayed on the grid ---
    var currentChord by mutableStateOf(Chord("G", "0-2-3-2"))
        private set

    var alternativeNames by mutableStateOf<List<String>>(emptyList())
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

    val slowChordDelay = 200.milliseconds
    val normalChordDelay = 100.milliseconds

    init {
        searchChord()

        synth.init()
        val sf2 = File(context.filesDir, "sound.sf2")
        if (!sf2.exists())
            context.assets.open("sound.sf2").use { i -> sf2.outputStream().use { i.copyTo(it) } }


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

        val names = nameChord(context, fingers)
            .split("-")
            .filter { it.isNotEmpty() }
            .sortedBy { it.length }

        currentChord = Chord(
            name = names.firstOrNull() ?: context.getString(R.string.no_chord_found_name),
            fingers = fingers
        )
        alternativeNames = names.drop(1)
    }

    /**
     * Changes the displayed chord and erases search result.
     * Called when a button is clicked on the grid.
     * @param fingers A list of strings representing the chord fingering (e.g., ["0", "2", "3", "2"]).
     * */
    fun changeFingering(fingers: List<String>) {
        displayFingering(fingers.joinToString("-"))
        searchResult = emptyList()
        showVisualizeButton = false
    }

    fun changeTextInput(newText: String) {
        textInput = newText
    }

    /**
     * Searches a chord by its name.
     * Called when clicking on the search button or a suggestion in the dropdown.*/
    fun searchChord() {
        showSuggestions = false
        val result = findChord2(textInput.lowercase())

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
            alternativeNames = emptyList()
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
            .filter { it.lowercase().contains(query) }
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

    fun playNote(key: Int = 60) {
        synth.noteOn(0, key, 100)
        Handler(Looper.getMainLooper()).postDelayed({ synth.noteOff(0, key) }, 3000)
    }

    suspend fun playChord(chord: Chord) {

        val fingers = chord.fingers.split("-").map { it.toInt() }
        val overtones = listOf<Int>(7, 0, 4, 9)
        for (i in 0..3) {
            playNote(fingers[i] + 60 + overtones[i])
            if (playSlowChord) {
                delay(slowChordDelay)
            }
            else {
                delay(normalChordDelay)
            }
        }
        playSlowChord = !playSlowChord
    }
}