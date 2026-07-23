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

package com.vipedev.kords.songs.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import com.vipedev.kords.chords.database.findChord2
import com.vipedev.kords.songs.SongsViewModel
import com.vipedev.kords.songs.database.Song


@Composable
fun DisplaySongScreen(viewModel: SongsViewModel, song: Song) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        //        HEADER BAR       //
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            // back button
            TextButton(
                onClick = { viewModel.resetCurrentSong() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            /*Text(
                text = stringResource(R.string.song_view_header),
                modifier = Modifier.padding(20.dp)
            )*/

            // edit and delete button
            Row (
                modifier = Modifier.align(Alignment.CenterEnd)
            ){
                TextButton(
                    onClick = {
                        viewModel.initEdition(song)
                    }
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                }

                TextButton(
                    onClick = {
                        viewModel.songToDelete = song
                        viewModel.showDeleteSongDialog = true

                    },
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                }
            }
        }

        if (viewModel.showDeleteSongDialog) {
            DeleteDialog(
                viewModel = viewModel,
                context = context
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
        ) {
            Text(
                text = song.title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = song.artist,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal
            )
        }

        //    STRUCTURE     //
        song.structure.forEach { (section, chords) ->
            if (section.isNotBlank() && chords.isNotEmpty()) {
                val sectionName = viewModel.getLocalizedSectionName(section)

                // section header
                Text(
                    text = sectionName,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // chords
                LazyRow (
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                ){
                    items(chords) { chord ->

                        TextButton(
                            modifier = Modifier
                                .wrapContentWidth(),
                            onClick = {
                                viewModel.chordInDialogName = chord
                                viewModel.showChordDialog = !viewModel.showChordDialog
                            }
                        ) {
                            Text(text = "$chord  ",
                                style = MaterialTheme.typography.bodySmall,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onBackground)
                        }

                        /*Text(text = "$chord  ",
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis)*/
                    }
                }
            }
        }

        if (viewModel.showChordDialog) {
            ChordGlimpse(viewModel = viewModel)
        }
    }
}

@Composable
fun ChordGlimpse(viewModel: SongsViewModel) {
    /*
    Creates an elevated ? to show the fingers of a chord.
    */

    val chordName = viewModel.chordInDialogName

    if (chordName.isBlank()) {
        return;
    }

    var text: String;
    val result = findChord2(chordName.lowercase())
    val isChord = result.isNotEmpty()
    text = if (!isChord) {
        stringResource(R.string.no_chord_found_name)
    }
    else {
        result[viewModel.chordDialogResultId].fingers
    }

    AlertDialog(
        title = {
            Text(text = text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        text = {
            Text(text = "G-C-E-A", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            viewModel.showChordDialog = false
            viewModel.chordDialogResultId = 0
            viewModel.chordInDialogName = ""
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.showChordDialog = false
                    viewModel.chordDialogResultId = 0
                    viewModel.chordInDialogName = ""
                }
            ) {
                Text("Fermer")
            }
        },
        confirmButton = {
            TextButton(
                enabled = isChord,
                onClick = {
                    viewModel.chordDialogResultId = (viewModel.chordDialogResultId + 1) % result.size
                }
            ) {
                Text("${viewModel.chordDialogResultId+1}/${result.size}")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    )


}
