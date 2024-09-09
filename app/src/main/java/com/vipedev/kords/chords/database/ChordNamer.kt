package com.vipedev.kords.chords.database

import android.content.Context
import com.vipedev.kords.R

val triads: Map<List<Int>, String> = mapOf(
    listOf(0, 4, 7) to "",
    listOf(0, 3, 7) to "m",
    listOf(0, 3, 6) to "dim",
)

val tetrads: Map<List<Int>, String> = mapOf(
    listOf(0, 4, 7, 12) to "",
    listOf(0, 3, 7, 12) to "m"
)

val sevens: Map<List<Int>, String> = mapOf(
    listOf(0, 4, 7, 10) to "7",
    listOf(0, 4, 7, 11) to "maj7",
    listOf(0, 3, 7, 10) to "m7",
    listOf(0, 3, 6, 10) to "m7b5",
    )

val reversed_1: Map<List<Int>, String> = mapOf(
    listOf(0, 5, 9) to "",
    listOf(0, 2, 6, 9) to "7",
    listOf(0, 1, 5, 8) to "maj7",
    listOf(0, 2, 5, 9) to "m7",
    listOf(0, 2, 5, 8) to "m7b5"
)

val reversed_2: Map<List<Int>, String> = mapOf(
    listOf(0, 3, 8) to "",
    listOf(0, 4, 9) to "m",
    listOf(0, 3, 6, 8) to "m7"
)

val stringToValue : Map<String, Int> = mapOf(
    "C" to 0,
    "C#" to 1,
    "Db" to 1,
    "D" to 2,
    "D#" to 3,
    "Eb" to 3,
    "E" to 4,
    "F" to 5,
    "F#" to 6,
    "Gb" to 6,
    "G" to 7,
    "G#" to 8,
    "Ab" to 8,
    "A" to 9,
    "A#" to 10,
    "Bb" to 10,
    "B" to 11
)

fun findFond(chord: List<Int>, type: String) : List<String> {
    val result: MutableList<String> = mutableListOf()

    when(type) {

        "r1" ->
            stringToValue.forEach { (name, id) ->
                if (id == chord[1]) {
                    result.add(name) }
            }

        "r2" ->
            stringToValue.forEach { (name, id) ->
                if (id == chord[2]) {
                    result.add(name)
                }
            }

        else -> stringToValue.forEach { (name, id) ->
            if (id == chord[0]) {
                result.add(name)
            }
        }
    }

    return result
}

fun nameChord(context: Context, fingers: String) : String {
    /**
     * Names a chord
     *
     * @param fingers The fingers of the chord. ("0-0-0-3")
     * @return [name] The name of the chord
     */

    val bchord: List<String> = fingers.split("-")
    val chord: MutableList<Int> = mutableListOf()

    bchord.forEachIndexed { index, s ->
        val i: Int = when (index) {
            0 -> 7
            1 -> 0
            2 -> 4
            3 -> 9
            else -> {
                0
            }
        }

        chord.add(index, s.toInt() + i)
    }
    chord.sort()

    val intervals = chord.map { it - chord[0] }.distinct()

    if (triads.containsKey(intervals)) {
        val fond = findFond(chord, type = "triad")
        return fond.joinToString(" - ") { fond -> fond + triads[intervals] }
    }

    if (tetrads.containsKey(intervals)) {
        val fond = findFond(chord, type = "tetrad")
        return fond.joinToString(" - ") { fond -> fond + tetrads[intervals] }
    }

    if (sevens.containsKey(intervals)) {
        val fond = findFond(chord, type = "seven")
        return fond.joinToString(" - ") { fond -> fond + sevens[intervals] }
    }

    if (reversed_1.containsKey(intervals)) {
        val fond = findFond(chord, type = "r1")
        return fond.joinToString(" - ") { fond -> fond + reversed_1[intervals] }
    }

    if (reversed_2.containsKey(intervals)) {
        val fond = findFond(chord, type = "r2")
        return fond.joinToString(" - ") { fond -> fond + reversed_2[intervals] }
    }

    return context.getString(R.string.no_chord_found_id)
}
