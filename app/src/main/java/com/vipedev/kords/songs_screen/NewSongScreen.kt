package com.vipedev.kords.songs_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NewSongScreen(viewModel: SongsViewModel) {

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

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                // back button
                TextButton(
                    onClick = {
                        viewModel.updateIsCreatingSong(false)
                        viewModel.resetCreation()
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }

                Text(
                    text = "Create a song",
                    modifier = Modifier.padding(20.dp)
                )

                // save button
                TextButton(
                    onClick = {
                        composableScope.launch {
                            viewModel.saveSong(
                                title = viewModel.titleField,
                                artist = viewModel.artistField,
                                structure = viewModel.struct,
                                context = context
                            )
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
                        text = "Title",
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
                singleLine = true
            )

            //     ARTIST TEXT FIELD      //
            OutlinedTextField(
                value = viewModel.artistField,
                onValueChange = { viewModel.updateArtistField(it) },
                label = {
                    Text(
                        text = "Artist",
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
                singleLine = true
            )
            //     EXISTING STRUCTURE ELEMENTS     //
            viewModel.struct.forEach { (type, chords) ->
                Text(text = type)
                Text(text = chords)
            }

            //     CURRENT STRUCTURE ELEMENT     //
            if (viewModel.currentStructType.isNotEmpty()) {
                Text(
                    text = viewModel.currentStructType,
                    modifier = Modifier.padding(20.dp)
                )

                OutlinedTextField(
                    value = viewModel.currentChords,
                    onValueChange = { viewModel.updateCurrentChords(it) },
                    label = {
                        Text(
                            text = "Type the chords, separated by a space",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RectangleShape,
                    textStyle = MaterialTheme.typography.labelLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.addStructItem(viewModel.currentStructType, viewModel.currentChords)
                        viewModel.resetStructElement()
                        focusManager.clearFocus()
                    })
                )
            }




            //     STRUCTURE TYPE DROPDOWN      //
            OutlinedButton(
                onClick = { viewModel.updateStructDropdownState(true) },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                shape = RectangleShape
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = "Choose a structure type")

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
                                viewModel.updateCurrentStructType(struct)
                                viewModel.updateStructDropdownState(false)

                            })
                    }
                }
            }
        }
    }
}