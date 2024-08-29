package com.vipedev.kords.settings_screen

import android.app.LocaleManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import java.util.Locale

class SettingsViewModel (
    dataStore: StorePreferences

) : ViewModel() {

    val dataStore = dataStore


    // list options
    var isDropdownVisible by mutableStateOf(false)
        private set

    private val languagesList :Map<String, String> = mapOf("fr" to "Français", "en" to "English")

    fun changeDropdownState(newState: Boolean) {
        isDropdownVisible = newState
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