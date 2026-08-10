package com.vipedev.kords

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vipedev.kords.chords.database.Chord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ChordPlayer(private val synth: Synth) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    var isPlaying by mutableStateOf(false)
        private set
    private val slowChordDelay = 200.milliseconds
    private val normalChordDelay = 100.milliseconds
    private var playSlow = false
    private var lastPlayed: List<Int> = emptyList()
    private var job: Job? = null

    fun playChord(chord: Chord) {
        job = scope.launch {
            isPlaying = true
            val fingers = chord.fingersToIntList()
            val overtones = listOf(7, 0, 4, 9)
            if (fingers == lastPlayed) {
                playSlow = !playSlow
            }
            try {
                for (i in 0..3) {
                    synth.noteOn(0, fingers[i] + 60 + overtones[i], 100)
                    delay(if (playSlow) slowChordDelay else normalChordDelay)
                }
                // playSlow = false
                delay(1000.milliseconds)
                lastPlayed = fingers
            } finally {
                isPlaying = false      // remis à false même si annulé
            }
        }
    }

    // à appeler quand on quitte l'écran
    fun stop() {
        job?.cancel()
        isPlaying = false
    }

    fun release() { scope.cancel(); synth.release() }
}