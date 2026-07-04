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

val equivalentRoots: Map<String, String> = mapOf(
    "ab" to "g#",
    "cb" to "b",
    "b#" to "c",
    "db" to "c#",
    "eb" to "d#",
    "fb" to "f",
    "e#" to "f",
    "gb" to "f#"
)

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

val chordTypes: List<String> = listOf("add9", "m7b5", "maj7", "aug", "dim", "m7", "7", "m", "", "sus2", "sus4", "7sus2", "7sus4")

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

val chordsListA: List<Chord> = listOf(
    Chord("A", "2-1-0-0"),
    Chord("A", "2-1-0-4"),
    Chord("A", "2-4-0-4"),
    Chord("A5", "2-4-0-0"),
    Chord("A7", "0-1-0-0"),
    Chord("A7", "2-4-3-4"),
    Chord("Asus4", "2-2-0-0"),
    Chord("A7sus4", "0-2-0-0"),
    Chord("A7sus4", "2-2-3-0"),
    Chord("A7sus4", "0-2-3-0"),
    Chord("Asus2", "2-4-0-2"),
    Chord("A7sus2", "2-4-3-2"),
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
    Chord("Amaj7", "2-4-4-4"))

val chordsListAd: List<Chord> = listOf(
    Chord("A#", "3-2-1-1"),
    Chord("A#7", "1-2-1-1"),
    Chord("A#7sus4", "1-3-1-1"),
    Chord("A#sus2", "3-0-1-1"),
    Chord("A#7sus2", "1-0-1-1"),
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
    Chord("A#sus4", "3-3-1-1"))

val chordsListB: List<Chord> = listOf(
    Chord("B", "4-3-2-2"),
    Chord("B7", "2-3-2-2"),
    Chord("B7", "4-3-2-0"),
    Chord("B7", "2-3-2-0"),
    Chord("B7sus4", "2-4-2-2"),
    Chord("B7sus4", "4-4-2-0"),
    Chord("B7sus4", "4-4-0-0"),
    Chord("B7sus4", "2-4-0-2"),
    Chord("B7sus2", "2-1-2-2"),
    Chord("Bsus2", "4-1-2-2"),
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
    Chord("Bsus4", "4-4-2-2"))

val chordsListC: List<Chord> = listOf(
    Chord("C", "0-0-0-3"),
    Chord("C", "0-4-0-3"),
    Chord("C", "0-4-3-3"),
    Chord("C5", "0-0-3-3"),
    Chord("C7", "0-0-0-1"),
    Chord("C7", "3-4-3-3"),
    Chord("C7", "3-4-3-1"),
    Chord("C7", "3-0-0-1"),
    Chord("C7sus4", "0-0-1-1"),
    Chord("C7sus4", "3-0-1-1"),
    Chord("C7sus4", "3-0-1-3"),
    Chord("C7sus2", "3-2-3-3"),
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
    Chord("Csus4", "0-0-1-3"),
    Chord("Csus2", "0-2-3-3"))

val chordsListCd: List<Chord> = listOf(
    Chord("C#", "1-1-1-4"),
    Chord("C#5", "1-1-4-4"),
    Chord("C#7", "1-1-1-2"),
    Chord("C#7", "4-1-1-2"),
    Chord("C#7", "4-1-1-4"),
    Chord("C#7sus4", "1-1-2-2"),
    Chord("C#7sus4", "4-1-2-2"),
    Chord("C#7sus4", "4-1-2-4"),
    Chord("C#7sus2", "4-3-4-4"),
    Chord("C#9", "1-3-1-2"),
    Chord("C#9", "4-3-1-4"),
    Chord("C#dim", "0-1-0-4"),
    Chord("C#dim", "0-4-0-4"),
    Chord("C#dim", "0-4-3-4"),
    Chord("C#m", "1-1-0-4"),
    Chord("C#m", "1-4-0-4"),
    Chord("C#m", "1-4-4-4"),
    Chord("C#m7", "1-1-0-2"),
    Chord("C#m7", "4-4-4-4"),
    Chord("C#m7", "4-1-0-2"),
    Chord("C#m7", "1-4-0-2"),
    Chord("C#m7b5", "0-1-0-2"),
    Chord("C#m7b5", "4-4-3-4"),
    Chord("C#m7b5", "0-4-3-2"),
    Chord("C#m7b5", "0-4-0-2"),
    Chord("C#maj7", "1-0-1-4"),
    Chord("C#maj7", "1-1-1-3"),
    Chord("C#maj7", "1-0-1-3"),
    Chord("C#sus4", "1-1-2-4"),
    Chord("C#sus2", "1-3-4-4"))

val chordsListD: List<Chord> = listOf(
    Chord("D", "2-2-2-0"),
    Chord("D7", "2-2-2-3"),
    Chord("D7", "2-0-2-0"),
    Chord("D7", "2-0-2-3"),
    Chord("D7sus4", "2-2-3-3"),
    Chord("D7sus4", "0-2-3-3"),
    Chord("Dsus2", "2-2-0-0"),
    Chord("D7sus2", "2-2-0-3"),
    Chord("Dm", "2-2-1-0"),
    Chord("Dm7", "2-2-1-3"),
    Chord("Dm7", "2-0-1-0"),
    Chord("Dm7", "2-0-1-3"),
    Chord("Dm7b5", "1-2-1-3"),
    Chord("Dm7b5", "1-0-1-3"),
    Chord("Dmaj7", "2-2-2-4"),
    Chord("Dmaj7", "2-1-2-4"),
    Chord("Dmaj7", "2-1-2-0"),
    Chord("Dsus4", "0-2-3-0"),
    Chord("Dsus4", "2-2-3-0"))

val chordsListDd: List<Chord> = listOf(
    Chord("D#", "0-3-3-1"),
    Chord("D#", "3-3-3-1"),
    Chord("D#7", "3-3-3-4"),
    Chord("D#7", "0-1-3-1"),
    Chord("D#7", "3-1-3-1"),
    Chord("D#7", "0-3-3-4"),
    Chord("D#7", "3-1-3-4"),
    Chord("D#7sus4", "3-3-4-4"),
    Chord("D#9", "0-1-1-1"),
    Chord("D#9", "0-3-1-4"),
    Chord("D#dim", "2-3-2-0"),
    Chord("D#m", "3-3-2-1"),
    Chord("D#m7", "3-3-2-4"),
    Chord("D#m7b5", "2-3-2-4"),
    Chord("D#sus4", "1-3-4-1"),
    Chord("D#sus4", "3-3-4-1"),
    Chord("D#sus2", "3-3-1-1"),
    Chord("D#7sus2", "3-3-1-4"))

val chordsListE: List<Chord> = listOf(
    Chord("E", "1-4-0-2"),
    Chord("E", "1-4-4-2"),
    Chord("E", "4-4-4-2"),
    Chord("E5", "4-4-0-2"),
    Chord("E7", "1-2-0-2"),
    Chord("E7", "1-2-4-2"),
    Chord("E7", "4-2-4-2"),
    Chord("E7sus4", "2-2-0-2"),
    Chord("E7sus4", "2-2-0-0"),
    Chord("E7sus4", "4-2-0-0"),
    Chord("E9", "1-2-2-2"),
    Chord("E9", "1-4-2-2"),
    Chord("Edim", "0-4-0-1"),
    Chord("Edim", "0-4-3-1"),
    Chord("Edim", "3-4-3-1"),
    Chord("Em", "0-4-3-2"),
    Chord("Em", "0-4-0-2"),
    Chord("Em", "4-4-3-2"),
    Chord("Em7", "0-2-0-2"),
    Chord("Em7", "0-2-3-2"),
    Chord("Em7b5", "0-2-0-1"),
    Chord("Em7b5", "0-2-3-1"),
    Chord("Em7b5", "3-2-3-1"),
    Chord("Emaj7", "1-3-0-2"),
    Chord("Emaj7", "4-3-4-2"),
    Chord("Emaj7", "1-3-4-2"),
    Chord("Esus4", "2-4-0-2"),
    Chord("Esus4", "4-4-0-0"),
    Chord("Esus2", "4-4-2-2"),
    Chord("E7sus2", "4-4-2-5"))

val chordsListF: List<Chord> = listOf(
    Chord("F", "2-0-1-0"),
    Chord("F", "2-0-1-3"),
    Chord("F7", "2-3-1-3"),
    Chord("F7", "2-3-1-0"),
    Chord("F7sus4", "3-3-1-3"),
    Chord("F7sus4", "3-3-1-1"),
    Chord("F9", "2-3-3-3"),
    Chord("F9", "0-3-1-0"),
    Chord("Fm", "1-0-1-3"),
    Chord("Fm7", "1-3-1-3"),
    Chord("Fm7", "1-3-4-3"),
    Chord("Fm7b5", "1-3-1-2"),
    Chord("Fmaj7", "2-4-1-3"),
    Chord("Fsus4", "3-0-1-1"),
    Chord("Fsus4", "3-0-1-3"),
    Chord("Fsus2", "0-0-1-3"),
    Chord("F7sus2", "0-3-1-3"))

val chordsListFd: List<Chord> = listOf(
    Chord("F#", "3-1-2-1"),
    Chord("F#", "3-1-2-4"),
    Chord("F#7", "3-4-2-4"),
    Chord("F#7sus4", "4-4-2-4"),
    Chord("F#7sus4", "4-4-2-2"),
    Chord("F#9", "1-1-0-1"),
    Chord("F#9", "3-4-4-4"),
    Chord("F#9", "1-4-2-1"),
    Chord("F#dim", "2-0-2-0"),
    Chord("F#dim", "2-0-2-3"),
    Chord("F#m", "2-1-2-0"),
    Chord("F#m", "2-1-2-4"),
    Chord("F#m7", "2-4-2-4"),
    Chord("F#m7b5", "2-4-2-3"),
    Chord("F#sus4", "4-1-2-2"),
    Chord("F#sus4", "4-1-2-4"),
    Chord("F#sus2", "1-1-2-4"),
    Chord("F#7sus2", "1-4-2-4"))

val chordsListG: List<Chord> = listOf(
    Chord("G", "0-2-3-2"),
    Chord("G", "4-2-3-2"),
    Chord("G7", "0-2-1-2"),
    Chord("G7sus4", "0-2-1-3"),
    Chord("G9", "2-2-3-2"),
    Chord("G9", "4-2-3-0"),
    Chord("G9", "4-2-1-0"),
    Chord("Gdim", "0-1-3-1"),
    Chord("Gdim", "3-1-3-1"),
    Chord("Gdim", "3-1-3-4"),
    Chord("Gm", "0-2-3-1"),
    Chord("Gm", "3-2-3-1"),
    Chord("Gm7", "0-2-1-1"),
    Chord("Gm7b5", "0-1-1-1"),
    Chord("Gmaj7", "0-2-2-2"),
    Chord("Gsus4", "0-2-3-3"),
    Chord("Gsus2", "0-2-3-0"),
    Chord("G7sus2", "0-2-1-0"))

val chordsListGd: List<Chord> = listOf(
    Chord("G#", "1-3-4-3"),
    Chord("G#7", "1-3-2-3"),
    Chord("G#7sus4", "1-3-2-4"),
    Chord("G#9", "1-0-2-1"),
    Chord("G#9", "3-3-4-3"),
    Chord("G#dim", "1-2-4-2"),
    Chord("G#dim", "4-2-4-2"),
    Chord("G#m", "4-3-4-2"),
    Chord("G#m", "1-3-4-2"),
    Chord("G#m7", "1-3-2-2"),
    Chord("G#m7b5", "1-2-2-2"),
    Chord("G#maj7", "0-3-4-3"),
    Chord("G#maj7", "1-3-3-3"),
    Chord("G#sus4", "1-3-4-4"),
    Chord("G#sus2", "1-3-4-1"),
    Chord("Csus2", "1-3-2-1"),
)

val chordsList: List<Chord> = chordsListA + chordsListAd + chordsListB + chordsListC + chordsListCd + chordsListD + chordsListDd + chordsListE + chordsListF + chordsListFd + chordsListG + chordsListGd
