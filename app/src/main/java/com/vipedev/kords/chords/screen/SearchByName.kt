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

package com.vipedev.kords.chords.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.vipedev.kords.R
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun SearchByName(viewModel: ChordsViewModel) {

    val focusManager = LocalFocusManager.current
    val composableScope = rememberCoroutineScope()

    Surface {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                //verticalAlignment = Alignment.CenterVertically
            ) {

                // text field and list of suggestions
                Column {
                    OutlinedTextField(
                        value = viewModel.chordSearched,
                        onValueChange = {
                            viewModel.changeChordSearched(it)
                            composableScope.launch { viewModel.delaySuggestions() }
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.search_bar_text),
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(30.dp),
                        textStyle = MaterialTheme.typography.labelLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    // List of suggestions

                    // filtering suggestions (if 2 chords have the same name, show only 1 in the suggestions)
                    val ogMatch = viewModel.getSuggestions().distinct()
                    val matchingChords = ogMatch.subList(0, min(3, ogMatch.size))

                    if (viewModel.chordSearched.isNotEmpty() && !viewModel.searched && matchingChords.isNotEmpty()) {

                        Suggestions(viewModel = viewModel, matchingChords = matchingChords.toMutableList(), focusManager = focusManager)

                    }
                }

                // search button
                Button(onClick = { viewModel.searchChord() },
                    shape = CircleShape,
                    modifier = Modifier.padding(top = 10.dp),
                    content = {
                        Icon(Icons.Default.Search, contentDescription = "content description", tint = MaterialTheme.colorScheme.onPrimary)
                    })
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (viewModel.searched) {
                Text(
                    text = viewModel.textState,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(400.dp)
                )



                if (viewModel.showVisualizeButton) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // change chord and visualize buttons
                    Row {
                        val nbResults = viewModel.searchResult.size
                        if (nbResults > 1) {
                            ChangeChordButton(viewModel = viewModel, right = false)
                        }

                        VisualizeButton(nbResults, viewModel)

                        if (viewModel.searchResult.size > 1) {
                            ChangeChordButton(viewModel = viewModel, right = true)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Suggestions(viewModel: ChordsViewModel, matchingChords: List<String>, focusManager: FocusManager) {


    DropdownMenu(
        expanded = viewModel.showSuggestions,
        modifier = Modifier
            .width(190.dp),
        onDismissRequest = { viewModel.showSuggestions = false},
        properties = PopupProperties(focusable = false),
        offset = DpOffset(x = 5.dp, y = 0.dp)
    ) {
        matchingChords.forEach { chord ->
            DropdownMenuItem(text = {
                Text(text = chord)
            }, onClick = {
                viewModel.changeChordSearched(chord)
                viewModel.searchChord()
                focusManager.clearFocus()
            })
        }
    }


}


