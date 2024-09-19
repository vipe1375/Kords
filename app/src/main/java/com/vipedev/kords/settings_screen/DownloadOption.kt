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

/*
@Composable
fun DownloadOption(viewModel: SettingsViewModel, dataStore: StorePreferences) {

    val title = stringResource(R.string.update_chords_title)
    val autoDownload : Boolean = dataStore.getAutoDownload.collectAsState(initial = true).value!!

    val scope = rememberCoroutineScope()

    OutlinedButton(
        onClick = {
            scope.launch { viewModel.downloadChords() }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
    )
    {
        Text(text = title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = if (viewModel.isDownloading) {
                    MaterialTheme.colorScheme.scrim
                } else {
                    MaterialTheme.colorScheme.onPrimary
                }

        )
    }
}

 */