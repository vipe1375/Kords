package com.vipedev.kords.chords.database

import android.content.Context
import com.vipedev.kords.R



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

    println(intervals)

    if (triads.containsKey(intervals)) {
        val fond = findFond(chord, type = "triad")
        return fond.joinToString(" - ") { it + triads[intervals] }
    }

    if (tetrads.containsKey(intervals)) {
        val fond = findFond(chord, type = "tetrad")
        return fond.joinToString(" - ") { it + tetrads[intervals] }
    }

    if (sevens.containsKey(intervals)) {
        val fond = findFond(chord, type = "seven")
        return fond.joinToString(" - ") { it + sevens[intervals] }
    }

    if (reversed_1.containsKey(intervals)) {
        val fond = findFond(chord, type = "r1")
        return fond.joinToString(" - ") { it + reversed_1[intervals] }
    }

    if (reversed_2.containsKey(intervals)) {
        val fond = findFond(chord, type = "r2")
        return fond.joinToString(" - ") { it + reversed_2[intervals] }
    }

    return context.getString(R.string.no_chord_found_id)
}


