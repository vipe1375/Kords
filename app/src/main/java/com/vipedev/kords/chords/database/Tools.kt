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
    listOf(0, 2, 5, 8) to "m7b5",
    listOf(0, 5, 7, 9) to "add9"
)

val reversed_2: Map<List<Int>, String> = mapOf(
    listOf(0, 3, 8) to "",
    listOf(0, 4, 9) to "m",
    listOf(0, 3, 6, 8) to "m7"
)

val chordTypes: List<String> = listOf("add9", "m7b5", "maj7", "aug", "dim", "m7", "7", "m", "")

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

/*
CHORDS_RAW = <<CHORDS_RAW
A         2,1,0,0  2,1,-,-
A         2,1,0,4  2,1,-,4
A         2,4,0,4  1,3,-,4
A 5       2,4,0,0  1,3,-,-
A 7       0,1,0,0  -,1,-,-
A 7       2,4,3,4  1,3,2,4
A sus     2,2,0,0  2,3,-,-
A 7sus    0,2,0,0  -,2,-,-
A 9       2,1,3,2  2,1,4,3
A 9       0,1,0,2  -,1,-,3
A 9       4,1,0,0  4,1,-,-
A aug     2,1,1,0  3,1,2,-
A aug     2,1,1,4  2,1,1,4
A dim7    2,3,2,3  1,3,2,4
A m       2,0,0,0  2,-,-,-
A m       2,0,0,3  2,-,-,4
A m       2,4,0,3  2,4,-,3
A m7      0,0,0,0
A m7      0,0,3,0  -,-,2,-
A m7      2,4,3,3  1,4,2,3
A m7b5    2,3,3,3  1,2,3,4
A maj7    1,1,0,0  1,2,-,-
A maj7    1,1,4,0  1,2,4,-
A maj7    1,1,0,4  1,2,-,4
A maj7    2,4,4,4  1,2,3,4

A#        3,2,1,1  3,2,1,1
A# 7      1,2,1,1  1,2,1,1
A# 7sus   1,3,1,1  1,3,1,1
A# 9      3,2,4,3  2,1,4,3
A# 9      1,2,1,3  1,2,1,4
A# aug    3,2,2,1  4,2,3,1
A# dim    3,1,0,1  4,1,-,2
A# dim    3,1,0,4  3,1,-,4
A# dim    3,4,0,4  1,2,-,3
A# dim7   0,1,0,1  -,1,-,2
A# dim7   3,4,3,4  1,3,2,4
A# m      3,1,1,1  3,1,1,1
A# m      3,1,1,4  3,1,1,4
A# m7     1,1,1,1  1,1,1,1
A# m7b5   1,1,0,1  1,2,-,3
A# m7b5   1,1,0,4  1,2,-,4
A# m7b5   1,4,0,4  1,3,-,4
A# m7b5   3,4,4,4  1,2,3,4
A# maj7   3,2,1,0  3,2,1,-
A# maj7   2,2,1,1  3,4,1,2
A# maj7   2,2,1,0  3,4,1,-
A# sus    3,3,1,1  3,4,1,1

B         4,3,2,2  3,2,1,1
B 7       2,3,2,2  1,2,1,1
B 7       4,3,2,0  3,2,1,-
B 7       2,3,2,0  1,3,2,-
B 7sus    2,4,2,2  1,3,1,1
B 7sus    4,4,2,0  3,4,1,-
B 7sus    4,4,0,0  3,4,-,-
B 7sus    2,4,0,2  1,4,-,2
B 9       2,3,2,4  1,3,2,4
B aug     4,3,3,2  4,2,3,1
B aug     0,3,3,2  -,2,3,1
B dim     4,2,1,2  4,2,1,3
B dim7    1,2,1,2  1,3,2,4
B m       4,2,2,2  3,1,1,1
B m7      2,2,2,2  1,1,1,1
B m7      4,2,2,0  4,1,2,-
B m7b5    2,2,1,2  2,3,1,4
B m7b5    4,2,1,0  4,2,1,-
B m7b5    2,2,1,0  2,3,1,-
B maj7    3,3,2,2  2,3,1,1
B maj7    4,3,2,1  4,3,2,1
B sus     4,4,2,2  3,4,1,1

C         0,0,0,3  -,-,-,3
C         0,4,0,3  -,2,-,1
C         0,4,3,3  -,2,1,1
C 5       0,0,3,3  -,-,1,1
C 7       0,0,0,1  -,-,-,1
C 7       3,4,3,3  1,2,1,1
C 7       3,4,3,1  2,4,3,1
C 7       3,0,0,1  3,-,-,1
C 7sus    0,0,1,1  -,-,1,1
C 7sus    3,0,1,1  3,-,1,1
C 7sus    3,0,1,3  3,-,1,4
C 9       3,0,0,1  3,-,-,1
C 9       0,2,0,1  -,2,-,1
C 9       3,2,0,3  2,1,-,3
C aug     1,0,0,3  1,-,-,3
C aug     1,4,0,3  1,4,-,3
C aug     1,4,4,3  1,3,4,2
C m       0,3,3,3  -,1,1,1
C m7      3,3,3,3  1,1,1,1
C m7      0,3,3,1  -,2,3,1
C m7      3,3,3,1  2,3,4,1
C m7b5    3,3,2,3  2,3,1,4
C m7b5    3,3,2,1  3,4,2,1
C maj7    0,0,0,2  -,-,-,2
C maj7    4,4,3,3  2,3,1,1
C maj7    4,0,0,2  3,-,-,1
C maj7    0,4,0,2  -,3,-,1
C sus     0,0,1,3  -,-,1,3

C#        1,1,1,4  1,1,1,4
C# 5      1,1,4,4  1,1,3,4
C# 7      1,1,1,2  1,1,1,2
C# 7      4,1,1,2  4,1,1,2
C# 7      4,1,1,4  3,1,1,4
C# 7sus   1,1,2,2  1,2,3,4
C# 7sus   4,1,2,2  4,1,3,2
C# 7sus   4,1,2,4  3,1,2,4
C# 9      1,3,1,2  1,3,1,2
C# 9      4,3,1,4  3,2,1,4
C# dim    0,1,0,4  -,1,-,4
C# dim    0,4,0,4  -,3,-,4
C# dim    0,4,3,4  -,3,2,4
C# m      1,1,0,4  1,2,-,4
C# m      1,4,0,4  1,3,-,4
C# m      1,4,4,4  1,4,4,4
C# m7     1,1,0,2  1,2,-,3
C# m7     4,4,4,4  1,1,1,1
C# m7     4,1,0,2  4,1,0,2
C# m7     1,4,0,2  1,4,0,2
C# m7b5   0,1,0,2  -,1,-,3
C# m7b5   4,4,3,4  2,3,1,4
C# m7b5   0,4,3,2  -,3,2,1
C# m7b5   0,4,0,2  -,3,-,1
C# maj7   1,0,1,4  1,-,2,4
C# maj7   1,1,1,3  1,1,1,3
C# maj7   1,0,1,3  1,0,2,4
C# sus    1,1,2,4  1,1,2,4

D         2,2,2,0  1,2,3,-
D 7       2,2,2,3  1,1,1,2
D 7       2,0,2,0  1,-,3,-
D 7       2,0,2,3  1,-,2,3
D 7sus    2,2,3,3  1,2,3,4
D 7sus    0,2,3,3  -,1,2,3
D m       2,2,1,0  2,3,1,-
D m7      2,2,1,3  2,3,1,4
D m7      2,0,1,0  2,-,1,-
D m7      2,0,1,3  2,-,1,4
D m7b5    1,2,1,3  1,2,1,3
D m7b5    1,0,1,3  1,0,2,4
D maj7    2,2,2,4  1,1,1,3
D maj7    2,1,2,4  2,1,3,4
D maj7    2,1,2,0  2,1,3,-
D sus     0,2,3,0  -,2,3,-
D sus     2,2,3,0  1,2,3,-

D#        0,3,3,1  -,3,4,1
D#        3,3,3,1  2,3,4,1
D# 7      3,3,3,4  1,1,1,2
D# 7      0,1,3,1  -,1,3,1
D# 7      3,1,3,1  3,1,4,1
D# 7      0,3,3,4  -,1,1,2
D# 7      3,1,3,4  2,1,3,4
D# 7sus   3,3,4,4  1,2,3,4
D# 9      0,1,1,1  -,1,1,1
D# 9      0,3,1,4  -,2,1,4
D# dim    2,3,2,0  2,1,3,-
D# m      3,3,2,1  3,4,2,1
D# m7     3,3,2,4  2,3,1,4
D# m7b5   2,3,2,4  1,3,2,4
D# sus    1,3,4,1  1,3,4,1
D# sus    3,3,4,1  2,3,4,1

E         1,4,0,2  1,4,-,2
E         1,4,4,2  1,4,3,2
E         4,4,4,2  2,3,4,1
E 5       4,4,0,2  3,4,-,1
E 7       1,2,0,2  1,2,-,3
E 7       1,2,4,2  1,2,4,3
E 7       4,2,4,2  3,1,4,2
E 7sus    2,2,0,2  1,2,-,3
E 7sus    2,2,0,0  2,3,-,-
E 7sus    4,2,0,0  3,1,-,-
E 9       1,2,2,2  1,2,3,4
E 9       1,4,2,2  1,4,2,3
E dim     0,4,0,1  -,4,-,1
E dim     0,4,3,1  -,4,3,1
E dim     3,4,3,1  2,4,3,1
E m       0,4,3,2  -,3,2,1
E m       0,4,0,2  -,3,-,1
E m       4,4,3,2  3,4,2,1
E m7      0,2,0,2  -,2,-,3
E m7      0,2,3,2  -,2,4,3
E m7b5    0,2,0,1  -,2,-,1
E m7b5    0,2,3,1  -,2,3,1
E m7b5    3,2,3,1  3,2,4,1
E maj7    1,3,0,2  1,3,-,2
E maj7    4,3,4,2  3,2,4,1
E maj7    1,3,4,2  1,3,4,2
E sus     2,4,0,2  1,4,-,2
E sus     4,4,0,0  3,4,-,-

F         2,0,1,0  2,-,1,-
F         2,0,1,3  2,-,1,4
F 7       2,3,1,3  2,3,1,4
F 7       2,3,1,0  2,3,1,-
F 7sus    3,3,1,3  2,3,1,4
F 7sus    3,3,1,1  3,4,1,1
F 9       2,3,3,3  1,2,3,4
F 9       0,3,1,0  -,3,1,-
F m       1,0,1,3  1,-,2,4
F m7      1,3,1,3  1,3,1,4
F m7      1,3,4,3  1,2,4,3
F m7b5    1,3,1,2  1,3,1,2
F maj7    2,4,1,3  2,4,1,3
F sus     3,0,1,1  3,-,1,1
F sus     3,0,1,3  3,-,1,4

F#        3,1,2,1  3,1,2,1
F#        3,1,2,4  3,1,2,4
F# 7      3,4,2,4  2,3,1,4
F# 7sus   4,4,2,4  2,3,1,4
F# 7sus   4,4,2,2  3,4,1,1
F# 9      1,1,0,1  1,2,-,3
F# 9      3,4,4,4  1,2,3,4
F# 9      1,4,2,1  1,4,2,1
F# dim    2,0,2,0  1,-,2,-
F# dim    2,0,2,3  1,-,2,4
F# m      2,1,2,0  2,1,3,-
F# m      2,1,2,4  2,1,3,4
F# m7     2,4,2,4  1,3,2,4
F# m7b5   2,4,2,3  1,3,1,2
F# sus    4,1,2,2  4,1,2,3
F# sus    4,1,2,4  3,1,2,4

G         0,2,3,2  -,1,3,2
G         4,2,3,2  3,1,2,1
G 7       0,2,1,2  -,2,1,3
G 7sus    0,2,1,3  -,2,1,3
G 9       2,2,3,2  1,1,2,1
G 9       4,2,3,0  3,1,2,-
G 9       4,2,1,0  4,2,1,-
G dim     0,1,3,1  -,1,3,2
G dim     3,1,3,1  4,1,3,2
G dim     3,1,3,4  2,1,3,4
G m       0,2,3,1  -,2,3,1
G m       3,2,3,1  2,3,4,1
G m7      0,2,1,1  -,2,1,1
G m7b5    0,1,1,1  -,1,1,1
G maj7    0,2,2,2  -,1,1,1
G sus     0,2,3,3  -,1,2,3

G#        1,3,4,3  1,2,4,3
G# 7      1,3,2,3  1,3,2,4
G# 7sus   1,3,2,4  1,3,2,4
G# 9      1,0,2,1  1,-,3,2
G# 9      3,3,4,3  1,1,2,1
G# dim    1,2,4,2  1,2,4,3
G# dim    4,2,4,2  3,1,4,2
G# m      4,3,4,2  3,2,4,1
G# m      1,3,4,2  1,3,4,2
G# m7     1,3,2,2  1,4,2,3
G# m7b5   1,2,2,2  1,2,3,4
G# maj7   0,3,4,3  -,1,3,2
G# maj7   1,3,3,3  1,2,3,4
G# sus    1,3,4,4  1,2,3,4
CHORDS_RAW
*/