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

/**
 * Finds a chord (for now only from the database)
 * @param name the name of the chord
 * @return List<Chord> a list of chords that match the name
 */
fun findChord(name: String): List<Chord> {
    return nameChordFromDatabase(name)
}

/**
 * Finds a chord in the database.
 * @param name the name of the chord
 * @return List<Chord> a list of chords that match the name
 */
private fun nameChordFromDatabase(name: String): List<Chord> {
    val (root, nonRootIndex) = findRoot(name)
    val trueName = if (nonRootIndex >= name.length) root
    else root + name.substring(nonRootIndex)
    return chordsList.filter { it.name.lowercase() == trueName }
}

/**
 * Finds the root of a chord from its name.
 * @param name the name of the chord
 * @return a pair <String, Int> with root and the index of the first character of the chord name that is not the root
 */
fun findRoot(name: String): Pair<String, Int> {
    if (name.isEmpty()) return Pair("", 0)
    if (name.length == 1) {
        return if (name in equivalentRoots) {
            Pair(equivalentRoots[name].toString(), 1)
        } else { Pair(name, 1) }
    }

    // length > 2
    val (root, index) = if (name[1] == 'b' || name[1] == '#') {
        Pair(name.slice(0..1), 2)
    }
    else {
        Pair(name.slice(0..0), 1)
    }
    return if (root in equivalentRoots) {
        Pair(equivalentRoots[root].toString(), index)
    } else { Pair(root, index) }
}

fun splitName(name: String) : MutableMap<String, String> {
    var nameCopy = name.lowercase()
    val result: MutableMap<String, String> = mutableMapOf()

    chordTypes.sortedByDescending { it.length }.forEach { type ->
        println("namec : $nameCopy, type : $type")
        if (nameCopy.contains(type)) {
            result["mod"] = type
            nameCopy = name.replace(type, "")
            result["root"] = nameCopy

            return result
        }
    }
    return result
}

fun getValidFrets(notes: List<Int>): List<List<Int>> {
    val valid: MutableList<MutableList<Int>> = mutableListOf()

    for (i in 0 until 4) {
        valid.add(mutableListOf())

        for (j in 0 until 14) {
            if ((stringTones[i] + j) % 12 in notes) {
                valid[i].add(j)
            }
        }
    }

    // each item in valid is a list of frets that play a note from the chord
    return valid
}

fun score(attempt: List<Int>, spacing: Boolean = true, position: Boolean = true): Int {
    var score = 0
    val spacingValue = attempt.sumOf { it - attempt.min() }
    val height = attempt.min()

    if (spacing) {
        score += spacingValue
    }

    if (position) {
        score += height
    }
    return score
}

fun getOptions(valid: List<List<Int>>, notes: List<Int>) : List<Pair<Int, List<Int>>> {
    val maxes = valid.map { it.size }
    val options: MutableList<Pair<Int, List<Int>>> = mutableListOf()
    if (0 !in maxes) {
        // if we can play all the notes in the chord

        // iterate over each possible combination of frets
        for (i in 0 until maxes[0]) {
            for (j in 0 until maxes[1]) {
                for (k in 0 until maxes[2]) {
                    for (l in 0 until maxes[3]) {
                        val attempt = listOf(valid[0][i], valid[1][j], valid[2][k], valid[3][l])

                        // missing notes in the chord
                        val mults = MutableList(12) { 0 }
                        for (a in 0 until 4) {
                            mults[(stringTones[a] + attempt[a]) % 12] += 1
                        }
                        println("mults : $mults")

                        // create set of remaining notes: notes that are not played in the current
                        // attempt but needed in the chord.
                        val remaining: MutableSet<Int> = mutableSetOf()

                        notes.forEach { note ->
                            if (mults[note] == 0) {
                                remaining.add(note)
                            }
                        }
                        println("remaining : $remaining")
                        if (remaining.size == 0) {
                            val score = score(attempt)
                            options.add(Pair(score, attempt))
                        }
                    }
                }
            }
        }
    }

    return options
}



private fun findChord2(name: String) : List<Chord> {
    val result: MutableList<Pair<Int, List<Int>>> = mutableListOf()
    val splitName = splitName(name)
    println("split : $splitName")

    // if chord wasn't correctly split
    if (splitName.isEmpty()) {
        return result.map { (_, _) -> Chord() }
    }

    val root: String = splitName["root"].toString()

    val rootId: Int = stringToValue[root] ?: return result.map { (_, _) -> Chord() }


    allIntervals.forEach { (intervals, type) ->
        if (type == splitName["mod"]) {
            val notes: MutableList<Int> = intervals.map { (it + rootId) % 12 }.toMutableList() // the notes (in half tones) that should be played

            val valid = getValidFrets(notes)

            val options = getOptions(valid, notes)
            options.forEach { result.add(it) }
        }
    }

    // remove duplicates, and sort by score :
    // - if the chord is high on the frets, it will have a higher score
    // - if the chord has a big spacing between frets, it will have a bad score
    val finalResults = result
        .distinct()
        .sortedBy { (score, _) -> score }
        .map { (_, fingers) -> Chord(name = name, fingers = fingers.joinToString("-")) }
    
    return finalResults.subList(0, minOf(5, finalResults.size))
}