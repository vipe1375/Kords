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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import com.vipedev.kords.songs.SongsViewModel

@Composable
fun EditSongScreen(viewModel: SongsViewModel) {

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current // only needed by DeleteDialog
    val lazyListState = rememberLazyListState()
    var isSectionMenuOpen by rememberSaveable { mutableStateOf(false) } // UI-only state

    val animatedAlpha by animateFloatAsState(
        targetValue = if (viewModel.isScreenVisible) 1.0f else 0f,
        label = "alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .graphicsLayer {
                alpha = animatedAlpha
            }
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        //        HEADER BAR       //
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // back button
            TextButton(
                onClick = { viewModel.cancelEditing() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            Text(
                text = stringResource(
                    if (viewModel.isNewSong) R.string.create_song_header else R.string.edit_song_header
                ),
                modifier = Modifier.padding(20.dp)
            )

            // save button
            TextButton(
                onClick = { viewModel.saveSong() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Default.Done, contentDescription = null)
            }
        }

        //     EXISTING STRUCTURE ELEMENTS     //
        Surface(
            modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            LazyColumn (
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen) // Required for blending
                    .drawWithContent {
                        drawContent() // Draw the actual buttons first
                        if (lazyListState.canScrollForward) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    0.8f to Color.Black, // Fully opaque until 80% of the height
                                    1f to Color.Transparent // Fade to transparent at the very bottom
                                ),
                                blendMode = BlendMode.DstIn // This "cuts" the content based on the brush opacity
                            )
                        }
                    },
            ) {
                item {

                    //     TITLE TEXT FIELD      //
                    OutlinedTextField(
                        value = viewModel.titleField,
                        onValueChange = { viewModel.titleField = it },
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
                        shape = RoundedCornerShape(10.dp),
                        textStyle = MaterialTheme.typography.labelLarge,
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {focusManager.clearFocus()}
                        )
                    )

                    //     ARTIST TEXT FIELD      //
                    OutlinedTextField(
                        value = viewModel.artistField,
                        onValueChange = { viewModel.artistField = it },
                        label = {
                            Text(
                                text = stringResource(R.string.create_song_artist_field),
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = MaterialTheme.typography.labelLarge,
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {focusManager.clearFocus()}
                        )
                    )

                }

                items(viewModel.struct.toList(), key = { it.first }) { (section, chords) ->

                    if (section.isNotBlank()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = viewModel.getLocalizedSectionName(section),
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            TextButton(
                                onClick = { viewModel.askDeleteSection(section) },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(Icons.Default.Delete,
                                    contentDescription = null,
                                )
                            }
                        }

                        TextField(
                            value = chords,
                            onValueChange = { viewModel.updateSection(section, it) },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.create_song_type_chords),
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(bottom = 5.dp))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            textStyle = MaterialTheme.typography.labelLarge,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                        )
                    }
                }

                item {Spacer(modifier = Modifier.height(20.dp))}
            }
        }



        //     CURRENT STRUCTURE ELEMENT     //

        if (viewModel.currentSection.isNotEmpty()) {
            Text(
                text = viewModel.currentSection,
                modifier = Modifier.padding(top = 16.dp, bottom = 5.dp, start = 20.dp)
            )

            TextField(
                value = viewModel.currentChords,
                onValueChange = { viewModel.currentChords = it },
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
                    if (viewModel.addStructItem()) focusManager.clearFocus()
                })
            )


        }

        if (viewModel.showDeleteSectionDialog) {
            DeleteDialog(viewModel, context)
        }

        //     STRUCTURE TYPE DROPDOWN      //
        OutlinedButton(
            onClick = { isSectionMenuOpen = true },
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = stringResource(R.string.create_song_choose_type))

            DropdownMenu(
                expanded = isSectionMenuOpen,
                onDismissRequest = { isSectionMenuOpen = false },
                modifier = Modifier
                    .wrapContentWidth(),
                offset = DpOffset(12.dp, 8.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                viewModel.sectionTypes.forEach { section ->
                    DropdownMenuItem(
                        text = { Text(text = viewModel.getLocalizedSectionName(section)) },
                        onClick = {
                            viewModel.currentSection = section
                            isSectionMenuOpen = false
                        })
                }
            }
        }
    }
}
