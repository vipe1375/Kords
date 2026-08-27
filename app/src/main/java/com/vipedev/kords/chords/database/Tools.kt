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
    "bb" to "a#",
    "b#" to "c",
    "db" to "c#",
    "eb" to "d#",
    "fb" to "e",
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
    listOf(0, 5, 7, 10) to "7sus4",
    listOf(0, 2, 7, 10) to "7sus2",
)

val extras: Map<List<Int>, String> = mapOf(
    listOf(0, 4, 7, 14) to "9",       // ou 9 si tu reduis mod 12
    listOf(0, 3, 6, 9)  to "dim7",
    listOf(0, 7)        to "5"
)
val allIntervalsFromName: Map<List<Int>, String> = triads + tetrads + sevens + extras

// read to create suggestions
val chordTypes: List<String> = allIntervalsFromName.map { it.value }.distinct()

val stringTones = listOf(7, 0, 4, 9)

val stringToValue : Map<String, Int> = mapOf(
    "c" to 0,
    "c#" to 1,
    "db" to 1,
    "d" to 2,
    "d#" to 3,
    "eb" to 3,
    "e" to 4,
    "f" to 5,
    "f#" to 6,
    "gb" to 6,
    "g" to 7,
    "g#" to 8,
    "ab" to 8,
    "a" to 9,
    "a#" to 10,
    "bb" to 10,
    "b" to 11
)

val allChords : MutableList<String> = stringToValue.keys.flatMap { root -> chordTypes.map { root + it }}.toMutableList()

val sharpNames: List<String> = listOf("c","c#","d","d#","e","f","f#","g","g#","a","a#","b")

val chordsListA: List<Chord> = listOf(
    Chord("a", "2-1-0-0"),
    Chord("a", "2-1-0-4"),
    Chord("a", "2-4-0-4"),
    Chord("a5", "2-4-0-0"),
    Chord("a7", "0-1-0-0"),
    Chord("a7", "2-4-3-4"),
    Chord("asus4", "2-2-0-0"),
    Chord("a7sus4", "0-2-0-0"),
    Chord("a7sus4", "2-2-3-0"),
    Chord("a7sus4", "0-2-3-0"),
    Chord("asus2", "2-4-0-2"),
    Chord("a7sus2", "2-4-3-2"),
    Chord("a9", "2-1-3-2"),
    Chord("a9", "0-1-0-2"),
    Chord("a9", "4-1-0-0"),
    Chord("aaug", "2-1-1-0"),
    Chord("aaug", "2-1-1-4"),
    Chord("adim7", "2-3-2-3"),
    Chord("am", "2-0-0-0"),
    Chord("am", "2-0-0-3"),
    Chord("am", "2-4-0-3"),
    Chord("am7", "0-0-0-0"),
    Chord("am7", "0-0-3-0"),
    Chord("am7", "2-4-3-3"),
    Chord("am7b5", "2-3-3-3"),
    Chord("amaj7", "1-1-0-0"),
    Chord("amaj7", "1-1-4-0"),
    Chord("amaj7", "1-1-0-4"),
    Chord("amaj7", "2-4-4-4"))

val chordsListAd: List<Chord> = listOf(
    Chord("a#", "3-2-1-1"),
    Chord("a#7", "1-2-1-1"),
    Chord("a#7sus4", "1-3-1-1"),
    Chord("a#sus2", "3-0-1-1"),
    Chord("a#7sus2", "1-0-1-1"),
    Chord("a#9", "3-2-4-3"),
    Chord("a#9", "1-2-1-3"),
    Chord("a#aug", "3-2-2-1"),
    Chord("a#dim", "3-1-0-1"),
    Chord("a#dim", "3-1-0-4"),
    Chord("a#dim", "3-4-0-4"),
    Chord("a#dim7", "0-1-0-1"),
    Chord("a#dim7", "3-4-3-4"),
    Chord("a#m", "3-1-1-1"),
    Chord("a#m", "3-1-1-4"),
    Chord("a#m7", "1-1-1-1"),
    Chord("a#m7b5", "1-1-0-1"),
    Chord("a#m7b5", "1-1-0-4"),
    Chord("a#m7b5", "1-4-0-4"),
    Chord("a#m7b5", "3-4-4-4"),
    Chord("a#maj7", "3-2-1-0"),
    Chord("a#maj7", "2-2-1-1"),
    Chord("a#maj7", "2-2-1-0"),
    Chord("a#sus4", "3-3-1-1"))

val chordsListB: List<Chord> = listOf(
    Chord("b", "4-3-2-2"),
    Chord("b7", "2-3-2-2"),
    Chord("b7", "4-3-2-0"),
    Chord("b7", "2-3-2-0"),
    Chord("b7sus4", "2-4-2-2"),
    Chord("b7sus4", "4-4-2-0"),
    Chord("b7sus4", "4-4-0-0"),
    Chord("b7sus4", "2-4-0-2"),
    Chord("b7sus2", "2-1-2-2"),
    Chord("bsus2", "4-1-2-2"),
    Chord("b9", "2-3-2-4"),
    Chord("baug", "4-3-3-2"),
    Chord("baug", "0-3-3-2"),
    Chord("bdim", "4-2-1-2"),
    Chord("bdim7", "1-2-1-2"),
    Chord("bm", "4-2-2-2"),
    Chord("bm7", "2-2-2-2"),
    Chord("bm7", "4-2-2-0"),
    Chord("bm7b5", "2-2-1-2"),
    Chord("bm7b5", "4-2-1-0"),
    Chord("bm7b5", "2-2-1-0"),
    Chord("bmaj7", "3-3-2-2"),
    Chord("bmaj7", "4-3-2-1"),
    Chord("bsus4", "4-4-2-2"))

val chordsListC: List<Chord> = listOf(
    Chord("c", "0-0-0-3"),
    Chord("c", "0-4-0-3"),
    Chord("c", "0-4-3-3"),
    Chord("c","5-4-3-3"),
    Chord("c5", "0-0-3-3"),
    Chord("c7", "0-0-0-1"),
    Chord("c7", "3-4-3-3"),
    Chord("c7", "3-4-3-1"),
    Chord("c7", "3-0-0-1"),
    Chord("c7sus4", "0-0-1-1"),
    Chord("c7sus4", "3-0-1-1"),
    Chord("c7sus4", "3-0-1-3"),
    Chord("c7sus2", "3-2-3-3"),
    Chord("c9", "3-0-0-1"),
    Chord("c9", "0-2-0-1"),
    Chord("c9", "3-2-0-3"),
    Chord("caug", "1-0-0-3"),
    Chord("caug", "1-4-0-3"),
    Chord("caug", "1-4-4-3"),
    Chord("cm", "0-3-3-3"),
    Chord("cm7", "3-3-3-3"),
    Chord("cm7", "0-3-3-1"),
    Chord("cm7", "3-3-3-1"),
    Chord("cm7b5", "3-3-2-3"),
    Chord("cm7b5", "3-3-2-1"),
    Chord("cmaj7", "0-0-0-2"),
    Chord("cmaj7", "4-4-3-3"),
    Chord("cmaj7", "4-0-0-2"),
    Chord("cmaj7", "0-4-0-2"),
    Chord("csus4", "0-0-1-3"),
    Chord("csus2", "0-2-3-3"),
    Chord("csus2", "1-3-2-1"),)

val chordsListCd: List<Chord> = listOf(
    Chord("c#", "1-1-1-4"),
    Chord("c#5", "1-1-4-4"),
    Chord("c#7", "1-1-1-2"),
    Chord("c#7", "4-1-1-2"),
    Chord("c#7", "4-1-1-4"),
    Chord("c#7sus4", "1-1-2-2"),
    Chord("c#7sus4", "4-1-2-2"),
    Chord("c#7sus4", "4-1-2-4"),
    Chord("c#7sus2", "4-3-4-4"),
    Chord("c#9", "1-3-1-2"),
    Chord("c#9", "4-3-1-4"),
    Chord("c#dim", "0-1-0-4"),
    Chord("c#dim", "0-4-0-4"),
    Chord("c#dim", "0-4-3-4"),
    Chord("c#m", "1-1-0-4"),
    Chord("c#m", "1-4-0-4"),
    Chord("c#m", "1-4-4-4"),
    Chord("c#m7", "1-1-0-2"),
    Chord("c#m7", "4-4-4-4"),
    Chord("c#m7", "4-1-0-2"),
    Chord("c#m7", "1-4-0-2"),
    Chord("c#m7b5", "0-1-0-2"),
    Chord("c#m7b5", "4-4-3-4"),
    Chord("c#m7b5", "0-4-3-2"),
    Chord("c#m7b5", "0-4-0-2"),
    Chord("c#maj7", "1-0-1-4"),
    Chord("c#maj7", "1-1-1-3"),
    Chord("c#maj7", "1-0-1-3"),
    Chord("c#sus4", "1-1-2-4"),
    Chord("c#sus2", "1-3-4-4"))

val chordsListD: List<Chord> = listOf(
    Chord("d", "2-2-2-0"),
    Chord("d7", "2-2-2-3"),
    Chord("d7", "2-0-2-0"),
    Chord("d7", "2-0-2-3"),
    Chord("d7sus4", "2-2-3-3"),
    Chord("d7sus4", "0-2-3-3"),
    Chord("dsus2", "2-2-0-0"),
    Chord("d7sus2", "2-2-0-3"),
    Chord("dm", "2-2-1-0"),
    Chord("dm7", "2-2-1-3"),
    Chord("dm7", "2-0-1-0"),
    Chord("dm7", "2-0-1-3"),
    Chord("dm7b5", "1-2-1-3"),
    Chord("dm7b5", "1-0-1-3"),
    Chord("dmaj7", "2-2-2-4"),
    Chord("dmaj7", "2-1-2-4"),
    Chord("dmaj7", "2-1-2-0"),
    Chord("dsus4", "0-2-3-0"),
    Chord("dsus4", "2-2-3-0"))

val chordsListDd: List<Chord> = listOf(
    Chord("d#", "0-3-3-1"),
    Chord("d#", "3-3-3-1"),
    Chord("d#7", "3-3-3-4"),
    Chord("d#7", "0-1-3-1"),
    Chord("d#7", "3-1-3-1"),
    Chord("d#7", "0-3-3-4"),
    Chord("d#7", "3-1-3-4"),
    Chord("d#7sus4", "3-3-4-4"),
    Chord("d#9", "0-1-1-1"),
    Chord("d#9", "0-3-1-4"),
    Chord("d#dim", "2-3-2-0"),
    Chord("d#m", "3-3-2-1"),
    Chord("d#m7", "3-3-2-4"),
    Chord("d#m7b5", "2-3-2-4"),
    Chord("d#sus4", "1-3-4-1"),
    Chord("d#sus4", "3-3-4-1"),
    Chord("d#sus2", "3-3-1-1"),
    Chord("d#7sus2", "3-3-1-4"))

val chordsListE: List<Chord> = listOf(
    Chord("e", "1-4-0-2"),
    Chord("e", "1-4-4-2"),
    Chord("e", "4-4-4-2"),
    Chord("e5", "4-4-0-2"),
    Chord("e7", "1-2-0-2"),
    Chord("e7", "1-2-4-2"),
    Chord("e7", "4-2-4-2"),
    Chord("e7sus4", "2-2-0-2"),
    Chord("e7sus4", "2-2-0-0"),
    Chord("e7sus4", "4-2-0-0"),
    Chord("e9", "1-2-2-2"),
    Chord("e9", "1-4-2-2"),
    Chord("edim", "0-4-0-1"),
    Chord("edim", "0-4-3-1"),
    Chord("edim", "3-4-3-1"),
    Chord("em", "0-4-3-2"),
    Chord("em", "0-4-0-2"),
    Chord("em", "4-4-3-2"),
    Chord("em7", "0-2-0-2"),
    Chord("em7", "0-2-3-2"),
    Chord("em7b5", "0-2-0-1"),
    Chord("em7b5", "0-2-3-1"),
    Chord("em7b5", "3-2-3-1"),
    Chord("emaj7", "1-3-0-2"),
    Chord("emaj7", "4-3-4-2"),
    Chord("emaj7", "1-3-4-2"),
    Chord("esus4", "2-4-0-2"),
    Chord("esus4", "4-4-0-0"),
    Chord("esus2", "4-4-2-2"),
    Chord("e7sus2", "4-4-2-5"))

val chordsListF: List<Chord> = listOf(
    Chord("f", "2-0-1-0"),
    Chord("f", "2-0-1-3"),
    Chord("f7", "2-3-1-3"),
    Chord("f7", "2-3-1-0"),
    Chord("f7sus4", "3-3-1-3"),
    Chord("f7sus4", "3-3-1-1"),
    Chord("f9", "2-3-3-3"),
    Chord("f9", "0-3-1-0"),
    Chord("fm", "1-0-1-3"),
    Chord("fm7", "1-3-1-3"),
    Chord("fm7", "1-3-4-3"),
    Chord("fm7b5", "1-3-1-2"),
    Chord("fmaj7", "2-4-1-3"),
    Chord("fsus4", "3-0-1-1"),
    Chord("fsus4", "3-0-1-3"),
    Chord("fsus2", "0-0-1-3"),
    Chord("f7sus2", "0-3-1-3"))

val chordsListFd: List<Chord> = listOf(
    Chord("f#", "3-1-2-1"),
    Chord("f#", "3-1-2-4"),
    Chord("f#7", "3-4-2-4"),
    Chord("f#7sus4", "4-4-2-4"),
    Chord("f#7sus4", "4-4-2-2"),
    Chord("f#9", "1-1-0-1"),
    Chord("f#9", "3-4-4-4"),
    Chord("f#9", "1-4-2-1"),
    Chord("f#dim", "2-0-2-0"),
    Chord("f#dim", "2-0-2-3"),
    Chord("f#m", "2-1-2-0"),
    Chord("f#m", "2-1-2-4"),
    Chord("f#m7", "2-4-2-4"),
    Chord("f#m7b5", "2-4-2-3"),
    Chord("f#sus4", "4-1-2-2"),
    Chord("f#sus4", "4-1-2-4"),
    Chord("f#sus2", "1-1-2-4"),
    Chord("f#7sus2", "1-4-2-4"))

val chordsListG: List<Chord> = listOf(
    Chord("g", "0-2-3-2"),
    Chord("g", "4-2-3-2"),
    Chord("g7", "0-2-1-2"),
    Chord("g7sus4", "0-2-1-3"),
    Chord("g9", "2-2-3-2"),
    Chord("g9", "4-2-3-0"),
    Chord("g9", "4-2-1-0"),
    Chord("gdim", "0-1-3-1"),
    Chord("gdim", "3-1-3-1"),
    Chord("gdim", "3-1-3-4"),
    Chord("gm", "0-2-3-1"),
    Chord("gm", "3-2-3-1"),
    Chord("gm7", "0-2-1-1"),
    Chord("gm7b5", "0-1-1-1"),
    Chord("gmaj7", "0-2-2-2"),
    Chord("gsus4", "0-2-3-3"),
    Chord("gsus2", "0-2-3-0"),
    Chord("g7sus2", "0-2-1-0"))

val chordsListGd: List<Chord> = listOf(
    Chord("g#", "1-3-4-3"),
    Chord("g#7", "1-3-2-3"),
    Chord("g#7sus4", "1-3-2-4"),
    Chord("g#9", "1-0-2-1"),
    Chord("g#9", "3-3-4-3"),
    Chord("g#dim", "1-2-4-2"),
    Chord("g#dim", "4-2-4-2"),
    Chord("g#m", "4-3-4-2"),
    Chord("g#m", "1-3-4-2"),
    Chord("g#m7", "1-3-2-2"),
    Chord("g#m7b5", "1-2-2-2"),
    Chord("g#maj7", "0-3-4-3"),
    Chord("g#maj7", "1-3-3-3"),
    Chord("g#sus4", "1-3-4-4"),
    Chord("g#sus2", "1-3-4-1"),
)

val chordsList: List<Chord> = chordsListA + chordsListAd + chordsListB + chordsListC + chordsListCd + chordsListD + chordsListDd + chordsListE + chordsListF + chordsListFd + chordsListG + chordsListGd
