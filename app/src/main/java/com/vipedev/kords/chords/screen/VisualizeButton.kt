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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

@Composable
fun VisualizeButton(
    nbResults: Int,
    viewModel: ChordsViewModel
) {
    Button(modifier = Modifier
        .padding(horizontal = 20.dp),
        onClick = {
            if (nbResults > 1) {
                val visualizedChord = viewModel.searchResult[viewModel.visualizedID - 1]
                viewModel.visualizeChord(visualizedChord)

            } else {
                viewModel.visualizeChord(viewModel.searchResult[0])

            }
            //focusManager.clearFocus()

        },
        content = {
            Text(
                LocalContext.current.resources.getQuantityString(
                    R.plurals.visualize_button_text,
                    viewModel.searchResult.size,   // quantity
                    viewModel.visualizedID,   // arg1
                    viewModel.searchResult.size
                )
            )  // arg2
        })
}

@Composable
fun ChangeChordButton(viewModel: ChordsViewModel, right: Boolean) {
    Button(onClick = { viewModel.changeVisualizedChord(right = right) },
        shape = CircleShape,

        ) {
        if (right) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        } else {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
        }

    }
}