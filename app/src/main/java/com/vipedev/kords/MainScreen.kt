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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.vipedev.kords.chords.screen.ChordScreen
import com.vipedev.kords.chords.screen.ChordsViewModel
import com.vipedev.kords.settings_screen.SettingsScreen
import com.vipedev.kords.settings_screen.SettingsViewModel
import com.vipedev.kords.settings_screen.StorePreferences
import com.vipedev.kords.songs_screen.MainSongScreen
import com.vipedev.kords.songs_screen.SongsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    items: List<BottomNavigationItem>,
    viewModel: ChordsViewModel,
    dataStore: StorePreferences,
    settingsViewModel: SettingsViewModel,
    songsViewModel: SongsViewModel
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold (
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = (selectedItemIndex == index),
                        onClick = {selectedItemIndex = index
                                  },
                        label = { Text(text = item.title) },
                        icon = {
                            if (index == selectedItemIndex) {
                                Icon(item.selectedIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                            } else {
                                Icon(item.unselectedIcon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedItemIndex == 1 && !songsViewModel.isEditingSong && songsViewModel.currentSong == null) {
                FloatingActionButton(
                    onClick = { songsViewModel.updateIsEditingSong(true) },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }
        }
    ){
        when(selectedItemIndex) {
            0 -> ChordScreen(viewModel = viewModel)
            1 -> MainSongScreen(songsViewModel)
            2 -> SettingsScreen(dataStore = dataStore, viewModel = settingsViewModel)
        }
    }
}