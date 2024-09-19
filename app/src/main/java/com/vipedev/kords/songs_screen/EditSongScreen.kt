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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import com.vipedev.kords.songs_screen.database.Song
import kotlinx.coroutines.launch

@Composable
fun EditSongScreen(viewModel: SongsViewModel, song: Song? = null) {

    val focusManager = LocalFocusManager.current
    val composableScope = rememberCoroutineScope()
    val context = LocalContext.current


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
                    onClick = {
                        if (viewModel.currentSong == null) {
                            viewModel.updateIsEditingSong(false)
                            viewModel.resetCreation()
                        }
                        else {
                            viewModel.updateIsEditingSong(false)
                            //viewModel.resetCurrentSong()
                        }

                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }

                val header: String = if (viewModel.currentSong == null) {stringResource(R.string.create_song_header)} else {
                    stringResource(R.string.edit_song_header)
                }
                Text(
                    text = header,
                    modifier = Modifier.padding(20.dp)
                )

                // save button
                TextButton(
                    onClick = {
                        composableScope.launch {
                            if (viewModel.currentSong == null) {
                                viewModel.saveSong(
                                    title = viewModel.titleField,
                                    artist = viewModel.artistField,
                                    structure = viewModel.struct,
                                    context = context
                                )
                            }

                            else {
                                viewModel.saveSong(
                                    title = viewModel.titleField,
                                    artist = viewModel.artistField,
                                    structure = viewModel.struct,
                                    context = context,
                                    viewModel.currentSong
                                )
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(Icons.Default.Done, contentDescription = null)
                }
            }

            //     TITLE TEXT FIELD      //
            OutlinedTextField(
                value = viewModel.titleField,
                onValueChange = { viewModel.updateTitleField(it) },
                label = {
                    Text(
                        text = stringResource(R.string.create_song_title),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(30.dp),
                textStyle = MaterialTheme.typography.labelLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                )
            )

            //     ARTIST TEXT FIELD      //
            OutlinedTextField(
                value = viewModel.artistField,
                onValueChange = { viewModel.updateArtistField(it) },
                label = {
                    Text(
                        text = stringResource(R.string.create_song_artist_field),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(30.dp),
                textStyle = MaterialTheme.typography.labelLarge,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {focusManager.clearFocus()}
                )
            )

            //     EXISTING STRUCTURE ELEMENTS     //
            LazyColumn {
                items(viewModel.struct.toList()) { (section, chords) ->

                    if (section.isNotBlank() && chords.isNotBlank()) {
                        var newChords by remember {
                            mutableStateOf(chords)
                        }
                        Text(
                            text = section,
                            modifier = Modifier
                                .padding(top = 20.dp, start = 20.dp, bottom = 10.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        TextField(
                            value = newChords,
                            onValueChange = { newChords = it },
                            placeholder = {
                                Text(
                                text = stringResource(R.string.create_song_type_chords),
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(bottom = 5.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            shape = CircleShape,
                            textStyle = MaterialTheme.typography.labelLarge,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = {
                                if (newChords.isNotBlank()) {
                                    viewModel.struct[section] = newChords
                                    focusManager.clearFocus()
                                } else {
                                    viewModel.displayToast(context = context, text = context.getString(R.string.create_song_no_chords))
                                }
                            })
                        )
                    }
                }
            }


            //     CURRENT STRUCTURE ELEMENT     //

            if (viewModel.currentStructType.isNotEmpty()) {
                Text(
                    text = viewModel.currentStructType,
                    modifier = Modifier.padding(top = 16.dp, bottom = 5.dp, start = 20.dp)
                )

                TextField(
                    value = viewModel.currentChords,
                    onValueChange = { viewModel.updateCurrentChords(it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.create_song_type_chords),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    shape = CircleShape,
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        if (viewModel.currentChords.isNotBlank()) {
                            viewModel.addStructItem()
                            focusManager.clearFocus()
                        }
                        else {
                            viewModel.displayToast(context = context, text = context.getString(R.string.create_song_no_chords))
                        }
                    })
                )
            }




            //     STRUCTURE TYPE DROPDOWN      //
            OutlinedButton(
                onClick = { viewModel.updateStructDropdownState(true) },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = stringResource(R.string.create_song_choose_type))

                DropdownMenu(
                    expanded = viewModel.structDropdownState,
                    onDismissRequest = { viewModel.updateStructDropdownState(false) },
                    modifier = Modifier
                        .size(width = 265.dp, height = 305.dp),
                    offset = DpOffset(0.dp, 8.dp)
                ) {
                    viewModel.structTypes.forEach { struct ->
                        DropdownMenuItem(
                            text = {Text(text = struct)},
                            onClick = {
                                //viewModel.struct[struct] = ""
                                viewModel.updateCurrentStructType(struct)
                                viewModel.updateStructDropdownState(false)

                            })
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}