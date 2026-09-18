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
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vipedev.kords.chords.ChordsViewModel
import com.vipedev.kords.chords.screen.ChordScreen
import com.vipedev.kords.settings.SettingsScreen
import com.vipedev.kords.settings.SettingsViewModel
import com.vipedev.kords.settings.StorePreferences
import com.vipedev.kords.songs.SongsViewModel
import com.vipedev.kords.songs.screen.MainSongScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    items: List<BottomNavigationItem>,
    viewModel: ChordsViewModel,
    dataStore: StorePreferences,
    settingsViewModel: SettingsViewModel,
    songsViewModel: SongsViewModel,
    selectedItemIndex: Int = 0,
    importUri: Uri? = null,
    onImportConsumed: () -> Unit = {}
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(selectedItemIndex)
    }

    val context = LocalContext.current

    LaunchedEffect(importUri) {
        importUri?.let { uri ->
            // Bascule sur l'onglet Chansons (même logique que ton onClick)
            selectedItemIndex = 1
            viewModel.isScreenVisible = false
            songsViewModel.isScreenVisible = true
            settingsViewModel.isScreenVisible = false

            songsViewModel.importSongFromUri(context, uri)
            onImportConsumed()
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 0.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = (selectedItemIndex == index),
                        onClick = {
                            selectedItemIndex = index
                            viewModel.isScreenVisible = index == 0
                            songsViewModel.isScreenVisible = index == 1
                            settingsViewModel.isScreenVisible = index == 2
                        },
                        label = { Text(text = item.title) },
                        icon = {
                            if (index == selectedItemIndex) {
                                Icon(item.selectedIcon, contentDescription = null)
                            } else {
                                Icon(item.unselectedIcon, contentDescription = null)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedItemIndex == 1 && !songsViewModel.isEditingSong && songsViewModel.currentSong == null) {
                // État pour gérer l'affichage du menu
                var isMenuExpanded by remember { mutableStateOf(false) }

                val context = LocalContext.current

                val importLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = { uri ->
                        uri?.let { songsViewModel.importSongFromUri(context, it) }
                    }
                )

                // FAB principal
                ExtendedFloatingActionButton(
                    onClick = { isMenuExpanded = true },
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    text = { Text("Ajouter") },
                    expanded = isMenuExpanded,
                    // onExpandedChange = { isMenuExpanded = it }
                )

                // Menu déroulant
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.create_song_button)) },
                        leadingIcon = { Icon(Icons.Filled.Add, contentDescription = null) },
                        onClick = {
                            songsViewModel.resetCreation()
                            songsViewModel.updateIsEditingSong(true)
                            isMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.import_song_button)) },
                        leadingIcon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                        onClick = {
                            // Lancer l'importation ici (ex: avec rememberLauncherForActivityResult)
                            importLauncher.launch("text/plain")
                            isMenuExpanded = false
                        }
                    )
                }
            }
        }
    ){
        innerPadding -> // 1. Capture the padding values here
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) { }
        Box(modifier = Modifier.padding(innerPadding)) { // 2. Apply them to a container
            AnimatedContent(
                targetState = selectedItemIndex,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) togetherWith
                            fadeOut(animationSpec = tween(500))
                },
                label = "screen_transition"
            ) { targetIndex ->
                when (targetIndex) {
                    0 -> ChordScreen(viewModel = viewModel)
                    1 -> MainSongScreen(songsViewModel)
                    2 -> SettingsScreen(dataStore = dataStore, viewModel = settingsViewModel)
                }
            }
        }
    }
}