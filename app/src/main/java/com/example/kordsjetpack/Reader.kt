package com.example.kordsjetpack

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

const val chords = """
    {
      "chords": [
        {"name": "c", "id": "0003", "displayName": "C"},
        {"name": "d", "id": "2220", "displayName": "D"},
        {"name": "f#m", "id": "2120", "displayName": "F#m"},
        {"name": "bm", "id": "4222", "displayName": "Bm"},
        {"name": "a", "id": "2100", "displayName": "A"},
        {"name": "f", "id": "2010", "displayName": "F"},
        {"name": "em", "id": "0432", "displayName": "Em"},
        {"name": "e", "id": "1402", "displayName": "E"},
        {"name": "cadd9", "id": "0013", "displayName": "Cadd9"},
        {"name": "f#7", "id": "3424", "displayName": "F#7"},
        {"name": "f#6", "id": "3324", "displayName": "F#6"},
        {"name": "c7", "id": "0001", "displayName": "C7"},
        {"name": "cmaj7", "id": "0002", "displayName": "Cmaj7"},
        {"name": "g7", "id": "0212", "displayName": "G7"},
        {"name": "gm7", "id": "0211", "displayName": "Gm7"},
        {"name": "dm", "id": "2210", "displayName": "Dm"},
        {"name": "am", "id": "2000", "displayName": "Am"},
        {"name": "g", "id": "0232", "displayName": "G"},
        {"name": "Em7", "id": "0475", "displayName": "Em7"},
        {"name": "aadd9", "id": "4454", "displayName": "Aadd9"},
        {"name": "d7", "id": "2253", "displayName": "D7"},
        {"name": "b", "id": "4322", "displayName": "B"},
        {"name": "cm", "id": "0333", "displayName": "Cm"}
      ]
    }
    """


data class Chord(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String
) {

    fun doesMatchQuery(query: String): Boolean {
       return (query.lowercase() in name)
    }
}

data class Dict(
    @SerializedName("chords") val chords: List<Chord>
)

fun readJson():Dict {
    val gson = Gson()
    val chords = gson.fromJson(chords, Dict::class.java)
    return chords
}

fun getChordByName(chord: String):String {
    val chords : Dict = readJson()

    chords.chords.forEach {
        if (it.name == chord) {
            return it.id
        }
    }
    return ""
}

fun getChordByID(id: String): String {
    val chords : Dict = readJson()

    chords.chords.forEach {
        if (it.id == id) {
            return it.displayName
        }
    }
    return ""
}