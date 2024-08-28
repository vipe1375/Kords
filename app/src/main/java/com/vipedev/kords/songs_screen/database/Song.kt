package com.vipedev.kords.songs_screen.database

import androidx.compose.ui.res.stringResource
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity
data class Song(

    val title: String,
    val artist: String,
    val structure: Map<String, List<String>>, // "Intro-1-2-3:Chorus-32-33    int are the ids of the chords

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

object Converters {
    @TypeConverter
    fun fromString(value: String?): Map<String, List<String>> {

        val list = value?.split(":")
        val result : MutableMap<String, List<String>> = mutableMapOf()

        list?.forEach { item ->

            val structElt = item.split("-")

            val structElt2 = structElt.drop(1)

            // val mappedStructElt = mapOf(structElt[0] to structElt2)

            result[structElt[0]] = structElt2
        }

        return result
    }

    @TypeConverter
    fun fromList(map: Map<String, List<String>>): String {
        var result = ""

        map.forEach { (type, chords) ->
            result += type
            chords.forEach { chordId ->
                result += "$chordId-"
            }
            result += ":"
        }
        return result
    }
}

