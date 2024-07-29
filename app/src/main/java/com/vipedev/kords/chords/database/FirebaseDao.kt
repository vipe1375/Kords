package com.vipedev.kords.chords.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

suspend fun fetchChordsData(): DataSnapshot? {
    try {
        val database = FirebaseDatabase.getInstance()


        val myRef = database.getReference("")
        val snapshot = myRef.get().await()

        return snapshot

    } catch (e: Exception) {
        println("error : $e")
        return null
    }
}

fun convertToChords(dataSnapshot: DataSnapshot): List<DataSnapshot> {

    val chordList = dataSnapshot.getChildren()
    val r = chordList.filter { it.getValue(Chord::class.java) != null }

    return r
}
