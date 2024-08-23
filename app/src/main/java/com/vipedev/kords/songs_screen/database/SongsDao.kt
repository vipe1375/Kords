package com.vipedev.kords.songs_screen.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {

    @Upsert
    suspend fun upsertSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM Song ORDER BY id DESC")
    fun getSongs_Id(): Flow<List<Song>>

    @Query("SELECT * FROM Song ORDER BY artist ASC")
    fun getSongs_Artist(): Flow<List<Song>>

    @Query("SELECT * FROM Song ORDER BY title ASC")
    fun getSongs_Title(): Flow<List<Song>>

}