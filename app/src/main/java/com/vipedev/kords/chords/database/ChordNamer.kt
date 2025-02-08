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

package com.vipedev.kords.chords.database

import android.content.Context
import com.vipedev.kords.R



fun findFond(chord: List<Int>, type: String) : List<String> {

    val result: MutableList<String> = mutableListOf()

    when(type) {

        "r1" ->
            stringToValue.forEach { (name, id) ->
                if (id == chord[1]%12) {
                    result.add(name) }
            }

        "r2" ->
            stringToValue.forEach { (name, id) ->
                if (id == chord[2]%12) {
                    result.add(name)
                }
            }

        "r3" ->
            stringToValue.forEach { (name, id) ->
                if (id == chord[3]%12) {
                    result.add(name)
                }
            }

        else -> stringToValue.forEach { (name, id) ->
            if (id == chord[0]%12) {
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

    // try to name the chord using the chords database
    val result = chordsList.filter {it.fingers == fingers}.map { it.name }
    if (result.isNotEmpty()) {
        if (result.size == 1) {
            return result[0]
        }
        val r = result.joinToString("-")
        return r
    }

    // no result from the database -> try to find it with the intervals
    val bchord: List<String> = fingers.split("-")
    val chord: MutableList<Int> = mutableListOf()

    bchord.forEachIndexed { index, s ->
        val i: Int = when (index) {
            0 -> 7
            1 -> 0
            2 -> 4
            3 -> 9
            else -> 0
        }

        chord.add(index, s.toInt() + i)
    }
    chord.sort()


    val intervals = chord.map { it - chord[0] }.distinct()


    val chordTypes = mapOf(
        "triad" to triads,
        "tetrad" to tetrads,
        "seven" to sevens,
        "r1" to reversed_1,
        "r2" to reversed_2,
        "r3" to reversed_3
    )
    val (type, matchingMap) = chordTypes.entries.firstOrNull { it.value.containsKey(intervals) } ?: return context.getString(R.string.no_chord_found_id)
    val fond = findFond(chord, type = type)
    val res = fond.joinToString(" - ") { it + matchingMap[intervals] }
    return res

}


