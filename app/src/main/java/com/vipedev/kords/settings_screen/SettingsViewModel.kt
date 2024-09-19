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