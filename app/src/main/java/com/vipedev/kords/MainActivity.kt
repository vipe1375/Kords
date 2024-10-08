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

package com.vipedev.kords

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.vipedev.kords.chords.screen.ChordsViewModel
import com.vipedev.kords.settings_screen.SettingsViewModel
import com.vipedev.kords.settings_screen.StorePreferences
import com.vipedev.kords.songs_screen.SongsViewModel
import com.vipedev.kords.songs_screen.database.SongsDao
import com.vipedev.kords.songs_screen.database.SongsDatabase
import com.vipedev.kords.ui.theme.KordsJetpackTheme


class MainActivity : ComponentActivity() {
/*

    // chords storing dao
    private val chordsDao: ChordsDao = ChordsDao()
*/
    // songs database dao
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            SongsDatabase::class.java,
            "songs.db"
        ).build()
    }


    private var updated: Boolean = false // if chords have been updated from firebase

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


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            val dataStore = StorePreferences(LocalContext.current)
            val useSystemTheme : Boolean = dataStore.getUseSystemTheme.collectAsState(initial = true).value!!
            val useDarkTheme : Boolean = dataStore.getUseDarkTheme.collectAsState(initial = true).value!!
            val darkTheme: Boolean = if (useSystemTheme) {
                isSystemInDarkTheme()
            } else {
                useDarkTheme
            }
            KordsJetpackTheme (darkTheme = darkTheme) {
                val items = listOf(
                    BottomNavigationItem(title = stringResource(id = R.string.chords_nav_item),
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false),
                    BottomNavigationItem(title = stringResource(id = R.string.songs_nav_item),
                        selectedIcon = Icons.Filled.PlayArrow,
                        unselectedIcon = Icons.Outlined.PlayArrow,
                        hasNews = false),
                    BottomNavigationItem(title = stringResource(id = R.string.settings_nav_item),
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = true)
                )


/*

                val autoDownload: Boolean = dataStore.getAutoDownload.collectAsState(initial = true).value!!
                println(autoDownload)
*/
/*

                // var isLoading by rememberSaveable { mutableStateOf(autoDownload) }
                var isLoading by rememberSaveable { mutableStateOf(true) }

                LaunchedEffect(isLoading) // Unit is the key which determines when the effect should be run
                {
                    val dataSnapshot: DataSnapshot? = fetchChordsData()

                    if (dataSnapshot == null) {
                        delay(5.seconds)
                    } else {
                        if (!updated) {
                            chordsDao.initChordList(convertToChords(dataSnapshot))
                            updated = true
                        }

                    }

                    isLoading = false
                }

                if (isLoading) {
                    LoadingScreen()
                } else {

                }
*/

                val viewModel: ChordsViewModel = viewModel(factory = ChordsViewModelFactory( this))
                val settingsViewModel = SettingsViewModel(dataStore = dataStore)
                val songsViewModel: SongsViewModel = viewModel(factory = SongsViewModelFactory(db.dao, this))
                MainScreen(items = items, viewModel = viewModel, dataStore = dataStore, settingsViewModel = settingsViewModel, songsViewModel = songsViewModel)

            }
        }
    }
}

class ChordsViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ChordsViewModel(context) as T
}

class SongsViewModelFactory(private val db: SongsDao, private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SongsViewModel(db, context) as T
}