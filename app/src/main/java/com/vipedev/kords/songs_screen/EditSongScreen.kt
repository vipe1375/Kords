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

import android.R.attr.alpha
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
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
import com.vipedev.kords.songs_screen.database.Song
import kotlinx.coroutines.launch

@Composable
fun EditSongScreen(viewModel: SongsViewModel) {

    val focusManager = LocalFocusManager.current
    val composableScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()

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
                            .padding(horizontal = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = MaterialTheme.typography.labelLarge,
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {focusManager.clearFocus()}
                        )
                    )

                }

                items(viewModel.struct.toList()) { (section, chords) ->

                    if (section.isNotBlank() && chords.isNotBlank()) {
                        var newChords by remember {
                            mutableStateOf(chords)
                        }

                        // split section (looking like "Chorus 1") into a section name and a number
                        // (name is always in french, no matter the app language)
                        val sectionSplit = section.split(" ")
                        val sectionNameEn: String = sectionSplit[0]
                        val number = if (sectionSplit.size > 1) " ${sectionSplit[1]}" else ""
                        val sectionName = when(sectionNameEn) {
                            "Refrain" -> stringResource(id = R.string.section_chorus) + number
                            "Couplet" -> stringResource(id = R.string.section_verse) + number
                            "Pont" -> stringResource(id = R.string.section_bridge) + number
                            "Solo" -> stringResource(id = R.string.section_solo) + number
                            "Intro" -> stringResource(id = R.string.section_intro) + number
                            "Outro" -> stringResource(id = R.string.section_outro) + number
                            else -> ""
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = sectionName,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 20.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            TextButton(
                                onClick = {
                                    viewModel.sectionToDelete = section
                                    viewModel.chordsToDelete = chords
                                    viewModel.showDeleteSectionDialog = true
                                },
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(Icons.Default.Delete,
                                    contentDescription = null,
                                )
                            }
                        }


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
                                .padding(horizontal = 10.dp),
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
                                    viewModel.showDeleteSectionDialog = true
                                    //viewModel.displayToast(context = context, text = context.getString(R.string.create_song_no_chords))
                                }
                            })
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
                        println("not blank")
                    }
                    else {
                        println("blank")
                        viewModel.showDeleteSectionDialog = true
                        //viewModel.displayToast(context = context, text = context.getString(R.string.create_song_no_chords))
                    }
                })
            )


        }

        if (viewModel.showDeleteSectionDialog) {
            DeleteDialog(viewModel, context)
        }

        //     STRUCTURE TYPE DROPDOWN      //
        OutlinedButton(
            onClick = { viewModel.updateStructDropdownState(true) },
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = stringResource(R.string.create_song_choose_type))

            DropdownMenu(
                expanded = viewModel.sectionDropdownState,
                onDismissRequest = { viewModel.updateStructDropdownState(false) },
                modifier = Modifier
                    .wrapContentWidth(),
                offset = DpOffset(12.dp, 8.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                viewModel.sectionTypes.forEach { section ->

                    val sectionName = when (section) {
                        "Intro" -> stringResource(id = R.string.section_intro)
                        "Refrain" -> stringResource(id = R.string.section_chorus)
                        "Couplet" -> stringResource(id = R.string.section_verse)
                        "Solo" -> stringResource(id = R.string.section_solo)
                        "Pont" -> stringResource(id = R.string.section_bridge)
                        "Outro" -> stringResource(id = R.string.section_outro)
                        else-> ""
                    }
                    DropdownMenuItem(
                        text = {Text(text = sectionName)},
                        onClick = {
                            //viewModel.struct[struct] = ""
                            viewModel.updateCurrentStructType(section)
                            viewModel.updateStructDropdownState(false)

                        })
                }
            }
        }

        //Spacer(modifier = Modifier.height(500.dp))
        // Text(text = stringResource(R.string.create_song_choose_type))
    }
}