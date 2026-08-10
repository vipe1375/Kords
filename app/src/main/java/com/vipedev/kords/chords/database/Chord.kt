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

data class Chord(
    val name: String = "",
    val fingers: String = ""
) {
    fun renderName(name: String = this.name): String {
        if (name.isBlank()) return ""
        return name[0].uppercaseChar() + name.substring(1)
    }

    fun fingersToIntList(fingers: String = this.fingers): MutableList<Int> {
        return fingers.split("-").map { it.toInt() } as MutableList<Int>
    }
}