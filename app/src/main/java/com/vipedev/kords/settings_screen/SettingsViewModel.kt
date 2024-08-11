package com.vipedev.kords.settings_screen

import android.app.LocaleManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import com.vipedev.kords.R
import com.vipedev.kords.chords.database.fetchChordsData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class SettingsViewModel (
    dataStore: StorePreferences

){
    val dataStore = dataStore

    var isDownloading by mutableStateOf(false)
        private set

    // list options
    var isDropdownVisible by mutableStateOf(false)
        private set

    private val languagesList :Map<String, String> = mapOf("fr" to "Français", "en" to "English")

    var autoDownload by mutableStateOf(true)
        private set

    fun changeDropdownState(newState: Boolean) {
        isDropdownVisible = newState
    }

    suspend fun downloadChords() {
        isDownloading = true
        delay(1000)
        isDownloading = false
    }

    fun localeSelection(context: Context, localeTag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }

    fun getLocaleName() : String {
        val currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
        val systemLocale = Locale.getDefault().displayCountry

        val locale : String = currentLocale?.toString()?.substring(0, 2) ?: systemLocale

        return languagesList[locale] ?: locale
    }
}