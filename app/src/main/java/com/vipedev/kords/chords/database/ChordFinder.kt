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
    // test to name chords algorithmically
    val resAlgo = nameChordAlgo(name)
    val resDB = nameChordFromDatabase(name)
    val res = (resAlgo + resDB).distinct()
    println(res)
    return res
    // return nameChordFromDatabase(name)
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
    return chordsList.filter { it.name.lowercase() == trueName }.map { Chord(it.name.lowercase(), it.fingers) }
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
    val (root, index) = findRoot(name)
    val mod = name.substring(index)
    return mutableMapOf("root" to root, "mod" to mod)
}

/**
 * Finds the valid (possible frets) for each note of a chord
 * @param notes a list of notes (in halftones) that should be played
 * @return list, where each item is a list of valid frets for a note
 */
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

/**
 * Evaluates the cost of fingers for a chord. Used in [nameChordAlgo] to rank the possible fingers.
 * The lower the cost, the better the fingers.
 * @param attempt the fingers to evaluate
 * @return the cost
 *
 * Cost:
 * - if the max. distance between two fingers is over 3 frets, the chord is considered unplayable.
 * - based on the height (lowest finger on the handle) and the spacing (sum of distances between fingers and the height)
 */
fun cost(attempt: List<Int>): Int {
    val spacingCosts = listOf<Int>(0, 1, 2, 3, 4, 6, 8, 10, 13, 16) // length 10 because max spacing is 9
    val pressed = attempt.filter{ it != 0 }
    val min = pressed.min()
    val max = pressed.max()

    if (max-min >= 4) return 100000;
    val spacing = pressed.sumOf { it - min }
    return min + spacingCosts[spacing]
}

/**
 * Returns the possible fingers for a chord given a list of valid frets.
 * Used in [nameChordAlgo]
 * @param valid the list of valid frets for each note of the chord (from [getValidFrets])
 * @param notes the notes to be played (in halftones)
 * @return a list of Pair(fingers, score) with fingers being a list of frets and score being the score of the chord (from [cost])
 */
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

                        // create set of remaining notes: notes that are not played in the current
                        // attempt but needed in the chord.
                        val remaining: MutableSet<Int> = mutableSetOf()

                        notes.forEach { note ->
                            if (mults[note] == 0) {
                                remaining.add(note)
                            }
                        }
                        if (remaining.size == 0) {
                            val score = cost(attempt)
                            options.add(Pair(score, attempt))
                        }
                    }
                }
            }
        }
    }

    return options
}

private fun nameChordAlgo(name: String) : List<Chord> {
    val result: MutableList<Pair<Int, List<Int>>> = mutableListOf()
    val splitName = splitName(name)

    // if chord wasn't correctly split
    if (splitName.isEmpty()) {
        return result.map { (_, _) -> Chord() }
    }

    val root: String = splitName["root"].toString()

    val uppercaseRoot = root[0].uppercase() + root.substring(1)
    // TODO: replace stringToValue (Tools.kt) with lowercase letters to avoid uppercasing and lowercasing

    val rootId: Int = stringToValue[uppercaseRoot] ?: return result.map { (_, _) -> Chord() }
    // works to this point

    println("[DEBUG] splitName: $splitName")
    allIntervalsFromName.forEach { (intervals, type) ->
        if (type == splitName["mod"]) {
            // the notes (in halftones) that should be played
            val notes: MutableList<Int> = intervals.map { (it + rootId) % 12 }.toMutableList()

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
        .sortedBy { (cost, _) -> cost }
        .map { (_, fingers) -> Chord(name = name, fingers = fingers.joinToString("-")) }

    println("[DEBUG] finalResults: $finalResults")
    return finalResults.subList(0, minOf(5, finalResults.size))
}