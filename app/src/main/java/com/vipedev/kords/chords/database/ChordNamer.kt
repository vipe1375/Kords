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

/**
 * Names a chord from its fingers, combining the database and the algorithm.
 * @param fingers the fingers of the chord ("0-0-0-3")
 * @return the matching chords, empty if none was found
 */
fun nameChord(fingers: String): List<Chord> {
    val fromDb = chordsList.filter { it.fingers == fingers }
    val result = (fromDb + nameFromIntervals(fingers)).distinctBy { it.name }.sortedBy { it.name.length }
    return result
}

/**
 * Names a chord by testing the 12 possible roots against the root-position intervals.
 * Exact inverse of the name -> fingers algorithm.
 */
private fun nameFromIntervals(fingers: String): List<Chord> {
    val frets = fingers.split("-").map { it.toInt() }
    val played = frets.mapIndexed { i, f -> (stringTones[i] + f) % 12 }.toSet()

    return buildList {
        for (root in 0 until 12) {
            allIntervalsFromName.forEach { (intervals, mod) ->
                if (played == intervals.map { (it + root) % 12 }.toSet()) {
                    add(Chord(sharpNames[root] + mod, fingers))
                }
            }
        }
    }
}


