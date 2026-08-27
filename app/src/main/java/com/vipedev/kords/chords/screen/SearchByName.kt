
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.vipedev.kords.R
import com.vipedev.kords.chords.ChordsViewModel
import com.vipedev.kords.chords.database.Chord
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByName(viewModel: ChordsViewModel) {
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    val suggestions = viewModel.getSuggestions()
        .distinct()
        .sortedBy { it.length }
        .take(3)

    // Every color the search field can use, kept in one place.
    /*val fieldColors = SearchBarDefaults.inputFieldColors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
    )*/


    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            bodyLarge = MaterialTheme.typography.labelLarge
        )
    ) {
        Column(
            //modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarDefaults.InputField(
                query = viewModel.textInput,
                onQueryChange = {
                    viewModel.changeTextInput(it)
                    viewModel.showSuggestions = it.isNotBlank()
                    scope.launch { viewModel.delaySuggestions() }
                },
                onSearch = {
                    viewModel.searchChord()
                    viewModel.showSuggestions = false
                    focusManager.clearFocus()
                },
                expanded = false,
                onExpandedChange = {},
                modifier = Modifier
                    .width(250.dp)
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = {
                    Text(
                        stringResource(R.string.search_bar_text),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (viewModel.textInput.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.changeTextInput("")
                            viewModel.showSuggestions = false
                        }) { Icon(Icons.Default.Clear, contentDescription = "Effacer") }
                    }
                },
            )

            // Popup anchored under the field; its height matches the item count.
            Suggestions(viewModel, suggestions, focusManager)
        }

    }

    Spacer(modifier = Modifier.height(20.dp))
}


/** Dropdown of matching chord names, shown only when there is something to suggest. */
@Composable
fun Suggestions(
    viewModel: ChordsViewModel,
    matchingChords: List<String>,
    focusManager: FocusManager
) {
    DropdownMenu(
        expanded = viewModel.showSuggestions && matchingChords.isNotEmpty(),
        modifier = Modifier
            .width(190.dp),
        onDismissRequest = { viewModel.showSuggestions = false },
        properties = PopupProperties(focusable = false), // keeps the keyboard open
        offset = DpOffset(x = 30.dp, y = 0.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        matchingChords.forEach { chord ->
            DropdownMenuItem(
                text = { Text(
                    Chord().renderName(chord),
                    style = MaterialTheme.typography.bodySmall
                ) },
                onClick = {
                    viewModel.changeTextInput(chord)
                    viewModel.searchChord()
                    focusManager.clearFocus()
                    viewModel.showSuggestions = false
                },
                colors = MenuItemColors(
                    textColor = MaterialTheme.colorScheme.onSurface,
                    leadingIconColor = MaterialTheme.colorScheme.surface,
                    trailingIconColor = MaterialTheme.colorScheme.surface,
                    disabledTextColor = MaterialTheme.colorScheme.surface,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.surface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    }
}



