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

package com.vipedev.kords.songs.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import org.json.JSONObject


@Entity
data class Song(

    val title: String,
    val artist: String,
    val structure: Map<String, List<String>>, // 1: ["G", "E", "A"], ...

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    fun toJson(): String = JSONObject().apply {
        put("format", FILE_FORMAT)   // used to separate random json from kords json
        put("version", FILE_VERSION) // database version
        put("title", title)
        put("artist", artist)
        put("structure", SongJson.structureToObj(structure))
    }.toString()

    companion object {
        const val FILE_FORMAT = "kords"
        const val FILE_VERSION = 1

        fun fromJson(json: String): Song {
            val obj = JSONObject(json)
            require(obj.optString("format") == FILE_FORMAT)
            return Song(
                title = obj.getString("title"),
                artist = obj.getString("artist"),
                structure = SongJson.structureFromObj(obj.getJSONObject("structure"))
            )
        }
    }
}

object Converters {
    @TypeConverter
    fun fromStructure(map: Map<String, List<String>>): String = SongJson.structureToJson(map)

    @TypeConverter
    fun toStructure(value: String?): Map<String, List<String>> =
        if (value.isNullOrBlank()) emptyMap() else SongJson.structureFromJson(value)
}

