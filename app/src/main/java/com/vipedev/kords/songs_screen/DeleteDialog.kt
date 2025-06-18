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

package com.vipedev.kords.songs_screen

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song

@Composable
fun DeleteDialog(viewModel: SongsViewModel, context: Context) {
    val showDialog = viewModel.showDeleteSongDialog || viewModel.showDeleteSectionDialog
    val isSong = viewModel.showDeleteSongDialog

    if (showDialog) {
        val titleText = if (isSong) stringResource(R.string.delete_song_dialog_header) else stringResource(
            R.string.delete_section_dialog_header
        )
        val messageText = if (isSong) stringResource(R.string.delete_song_dialog_text) else stringResource(
            R.string.delete_section_dialog_text
        )
        val onConfirm = {
            if (isSong) {
                if (viewModel.songToDelete != null) {
                    viewModel.deleteSong(viewModel.songToDelete!!, context)
                    viewModel.songToDelete = null
                }
                viewModel.showDeleteSongDialog = false
            } else {
                //viewModel.updateCurrentChords("")
                //viewModel.updateCurrentStructType("")
                if (viewModel.sectionToDelete != null && viewModel.chordsToDelete != null) {
                    viewModel.struct.remove(viewModel.sectionToDelete, viewModel.chordsToDelete)
                }
                viewModel.showDeleteSectionDialog = false
            }
        }
        val onDismiss = {
            if (isSong) viewModel.showDeleteSongDialog = false
            else viewModel.showDeleteSectionDialog = false
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = titleText) },
            text = { Text(text = messageText,
                style = MaterialTheme.typography.bodySmall) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(stringResource(R.string.confirm_button_text), color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel_button_text), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )
    }
}