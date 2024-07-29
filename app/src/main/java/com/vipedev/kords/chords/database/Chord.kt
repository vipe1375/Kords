package com.vipedev.kords.chords.database

data class Chord(
    val name: String = "",
    val id: Int = 0,
    val fingers: String = ""
) {

    fun doesMatchNameQuery(query: String): Boolean {
        return (query.lowercase() in name.lowercase())
    }

    fun doesMatchFingersQuery(query: String): Boolean {
        return (query == fingers)
    }
}