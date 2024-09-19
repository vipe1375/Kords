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

package com.vipedev.kords.settings_screen

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StorePreferences (
    private val context: Context
) {

    // pour être sûr qu'il n'y a qu'une instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userPreferences")
        val USE_SYSTEM_THEME_KEY = booleanPreferencesKey("use_system_theme")
        val USE_DARK_THEME_KEY = booleanPreferencesKey("use_dark_theme")
        val AUTO_DOWNLOAD_KEY = booleanPreferencesKey("auto_download")
    }

    val getUseSystemTheme: Flow<Boolean?> = context.dataStore.data.map {
        preferences -> preferences[USE_SYSTEM_THEME_KEY] ?: false
    }

    suspend fun saveUseSystemTheme(state: Boolean) {
        context.dataStore.edit { preferences -> preferences[USE_SYSTEM_THEME_KEY] = state }
    }

    val getUseDarkTheme: Flow<Boolean?> = context.dataStore.data.map {
            preferences -> preferences[USE_DARK_THEME_KEY] ?: false
    }

    suspend fun saveUseDarkTheme(state: Boolean) {
        context.dataStore.edit { preferences -> preferences[USE_DARK_THEME_KEY] = state }
    }

    val getAutoDownload: Flow<Boolean?> = context.dataStore.data.map {
            preferences -> preferences[AUTO_DOWNLOAD_KEY] ?: false
    }

    suspend fun saveAutoDownload(state: Boolean) {
        context.dataStore.edit { preferences -> preferences[AUTO_DOWNLOAD_KEY] = state }
    }
}