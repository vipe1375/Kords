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

val triads: Map<List<Int>, String> = mapOf(
    listOf(0, 4, 7) to "",
    listOf(0, 4, 8) to "aug",
    listOf(0, 2, 7) to "sus2",
    listOf(0, 5, 7) to "sus4",
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
    listOf(0, 5, 8) to "m",
    listOf(0, 5, 8, 12) to "m",
    listOf(0, 2, 6, 9) to "7",
    listOf(0, 1, 5, 8) to "maj7",
    listOf(0, 2, 5, 9) to "m7",
    listOf(0, 2, 5, 8) to "m7b5",
    listOf(0, 5, 7, 9) to "add9"
)

val reversed_2: Map<List<Int>, String> = mapOf(
    listOf(0, 3, 8) to "",
    listOf(0, 4, 9) to "m",
)

val reversed_3: Map<List<Int>, String> = mapOf(
    listOf(0, 3, 6, 8) to "7"
)

val allIntervals: Map<List<Int>, String> = triads + tetrads + sevens + reversed_1 + reversed_2 + reversed_3

val chordTypes: List<String> = listOf("add9", "m7b5", "maj7", "aug", "dim", "m7", "7", "m", "", "sus2", "sus4")

val stringTones = listOf(7, 0, 4, 9)

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

val allChords : MutableList<String> = stringToValue.keys.flatMap { root -> chordTypes.map { root + it }}.toMutableList()

val chordsList: List<Chord> = listOf(
    Chord("A", "2-1-0-0"),
    Chord("A", "2-1-0-4"),
    Chord("A", "2-4-0-4"),
    Chord("A5", "2-4-0-0"),
    Chord("A7", "0-1-0-0"),
    Chord("A7", "2-4-3-4"),
    Chord("Asus", "2-2-0-0"),
    Chord("A7sus", "0-2-0-0"),
    Chord("A9", "2-1-3-2"),
    Chord("A9", "0-1-0-2"),
    Chord("A9", "4-1-0-0"),
    Chord("Aaug", "2-1-1-0"),
    Chord("Aaug", "2-1-1-4"),
    Chord("Adim7", "2-3-2-3"),
    Chord("Am", "2-0-0-0"),
    Chord("Am", "2-0-0-3"),
    Chord("Am", "2-4-0-3"),
    Chord("Am7", "0-0-0-0"),
    Chord("Am7", "0-0-3-0"),
    Chord("Am7", "2-4-3-3"),
    Chord("Am7b5", "2-3-3-3"),
    Chord("Amaj7", "1-1-0-0"),
    Chord("Amaj7", "1-1-4-0"),
    Chord("Amaj7", "1-1-0-4"),
    Chord("Amaj7", "2-4-4-4"),

    Chord("A#", "3-2-1-1"),
    Chord("A#7", "1-2-1-1"),
    Chord("A#7sus", "1-3-1-1"),
    Chord("A#9", "3-2-4-3"),
    Chord("A#9", "1-2-1-3"),
    Chord("A#aug", "3-2-2-1"),
    Chord("A#dim", "3-1-0-1"),
    Chord("A#dim", "3-1-0-4"),
    Chord("A#dim", "3-4-0-4"),
    Chord("A#dim7", "0-1-0-1"),
    Chord("A#dim7", "3-4-3-4"),
    Chord("A#m", "3-1-1-1"),
    Chord("A#m", "3-1-1-4"),
    Chord("A#m7", "1-1-1-1"),
    Chord("A#m7b5", "1-1-0-1"),
    Chord("A#m7b5", "1-1-0-4"),
    Chord("A#m7b5", "1-4-0-4"),
    Chord("A#m7b5", "3-4-4-4"),
    Chord("A#maj7", "3-2-1-0"),
    Chord("A#maj7", "2-2-1-1"),
    Chord("A#maj7", "2-2-1-0"),
    Chord("A#sus", "3-3-1-1"),

    Chord("B", "4-3-2-2"),
    Chord("B7", "2-3-2-2"),
    Chord("B7", "4-3-2-0"),
    Chord("B7", "2-3-2-0"),
    Chord("B7sus", "2-4-2-2"),
    Chord("B7sus", "4-4-2-0"),
    Chord("B7sus", "4-4-0-0"),
    Chord("B7sus", "2-4-0-2"),
    Chord("B9", "2-3-2-4"),
    Chord("Baug", "4-3-3-2"),
    Chord("Baug", "0-3-3-2"),
    Chord("Bdim", "4-2-1-2"),
    Chord("Bdim7", "1-2-1-2"),
    Chord("Bm", "4-2-2-2"),
    Chord("Bm7", "2-2-2-2"),
    Chord("Bm7", "4-2-2-0"),
    Chord("Bm7b5", "2-2-1-2"),
    Chord("Bm7b5", "4-2-1-0"),
    Chord("Bm7b5", "2-2-1-0"),
    Chord("Bmaj7", "3-3-2-2"),
    Chord("Bmaj7", "4-3-2-1"),
    Chord("Bsus", "4-4-2-2"),

    Chord("C", "0-0-0-3"),
    Chord("C", "0-4-0-3"),
    Chord("C", "0-4-3-3"),
    Chord("C5", "0-0-3-3"),
    Chord("C7", "0-0-0-1"),
    Chord("C7", "3-4-3-3"),
    Chord("C7", "3-4-3-1"),
    Chord("C7", "3-0-0-1"),
    Chord("C7sus", "0-0-1-1"),
    Chord("C7sus", "3-0-1-1"),
    Chord("C7sus", "3-0-1-3"),
    Chord("C9", "3-0-0-1"),
    Chord("C9", "0-2-0-1"),
    Chord("C9", "3-2-0-3"),
    Chord("Caug", "1-0-0-3"),
    Chord("Caug", "1-4-0-3"),
    Chord("Caug", "1-4-4-3"),
    Chord("Cm", "0-3-3-3"),
    Chord("Cm7", "3-3-3-3"),
    Chord("Cm7", "0-3-3-1"),
    Chord("Cm7", "3-3-3-1"),
    Chord("Cm7b5", "3-3-2-3"),
    Chord("Cm7b5", "3-3-2-1"),
    Chord("Cmaj7", "0-0-0-2"),
    Chord("Cmaj7", "4-4-3-3"),
    Chord("Cmaj7", "4-0-0-2"),
    Chord("Cmaj7", "0-4-0-2"),
    Chord("Csus", "0-0-1-3")
)