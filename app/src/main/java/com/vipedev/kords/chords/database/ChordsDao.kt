package com.vipedev.kords.chords.database

import com.google.firebase.database.DataSnapshot

class ChordsDao {
    private var chordList: MutableList<Chord> = mutableListOf()

    fun initChordList(cL: List<DataSnapshot>) {
        cL.forEach { chord ->
            if (chord.getValue(Chord::class.java) != null) {
                chordList.add(chord.getValue(Chord::class.java)!!)
            }
        }
    }

    fun getChordsByName(name: String): List<Chord> {
        val r = chordList.filter { it.name.lowercase() == name }
        return r
    }

    fun getSuggestions(name: String): List<Chord> {
        val r = chordList.filter { it.doesMatchNameQuery(name) }
        return r
    }

    fun getChordsByFingers(fingers: String): List<Chord> {
        val result: List<Chord> = chordList.filter { it.doesMatchFingersQuery(fingers) }

        return result
    }
}