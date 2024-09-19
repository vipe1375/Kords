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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song

@Composable
fun DisplaySongScreen(viewModel: SongsViewModel, song: Song) {

    val focusManager = LocalFocusManager.current
    val composableScope = rememberCoroutineScope()
    val context = LocalContext.current

    println("struct deb display : ${song.structure}")
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

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

                Text(
                    text = stringResource(R.string.song_view_header),
                    modifier = Modifier.padding(20.dp)
                )

                // edit and delete button
                Row (
                    modifier = Modifier.align(Alignment.CenterEnd)
                ){
                    TextButton(
                        onClick = {
                            viewModel.initCurrentSong(song)
                            viewModel.updateIsEditingSong(true)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }

                    TextButton(
                        onClick = { viewModel.deleteSong(song, context = context) },
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }


                }


            }

            //     TITLE     //
            Text(text = stringResource(R.string.create_song_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, bottom = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold)

            Text(text = song.title,
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold)


            //     ARTIST     //
            Text(text = stringResource(R.string.create_song_artist_field),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, bottom = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold)

            Text(text = song.artist,
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold)


            //    STRUCTURE     //
            song.structure.forEach { (section, chords) ->

                // section header
                Text(
                    text = section,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, bottom = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // chords
                LazyRow (
                    modifier = Modifier.padding(horizontal = 20.dp)
                ){
                    items(chords) { chord ->
                        Text(text = "$chord  ",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}