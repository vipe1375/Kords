package com.vipedev.kords

import android.annotation.SuppressLint
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
import com.vipedev.kords.songs_screen.SongsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    items: List<BottomNavigationItem>,
    viewModel: ChordsViewModel,
    dataStore: StorePreferences,
    settingsViewModel: SettingsViewModel
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
        }
    ){
        when(selectedItemIndex) {
            0 -> ChordScreen(viewModel = viewModel)
            1 -> SongsScreen()
            2 -> SettingsScreen(dataStore = dataStore, viewModel = settingsViewModel)
        }
    }
}