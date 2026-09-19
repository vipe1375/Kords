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

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Song::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class SongsDatabase:RoomDatabase() {

    abstract val dao: SongsDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val updates = mutableListOf<Pair<Int, String>>()
        db.query("SELECT id, structure FROM Song").use { c ->
            while (c.moveToNext()) {
                val old = c.getString(1)
                if (old.trimStart().startsWith("{")) continue // déjà en JSON
                updates += c.getInt(0) to
                        SongJson.structureToJson(LegacyFormat.structureFromString(old))
            }
        }
        updates.forEach { (id, json) ->
            db.execSQL("UPDATE Song SET structure = ? WHERE id = ?", arrayOf<Any?>(json, id))
        }
    }
}