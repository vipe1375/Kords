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