package com.vipedev.kords.songs_screen.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Song::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SongsDatabase:RoomDatabase() {

    abstract val dao: SongsDao
}