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

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vipedev.kords.chords.ChordsViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchByID(viewModel: ChordsViewModel) {
    val slotSize = 64.dp        // emprise d'une colonne, identique dans les deux etats
    val activeSize = 56.dp      // ce que tu avais visuellement avant
    val inactiveSize = 52.dp

    val currentChord = viewModel.currentChord
    val scrollState = rememberScrollState()


    Surface (
        modifier = Modifier
            .padding(all = 20.dp)
            .clip(RoundedCornerShape(15.dp))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen) // Required for blending
                .drawWithContent {
                    drawContent() // Draw the actual buttons first
                    if (scrollState.canScrollForward) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                0.8f to Color.Black, // Fully opaque until 80% of the height
                                1f to Color.Transparent // Fade to transparent at the very bottom
                            ),
                            blendMode = BlendMode.DstIn // This "cuts" the content based on the brush opacity
                        )
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // STRING NAMES
            Spacer(modifier = Modifier.height(30.dp))

            val stringNamesSpacing: Dp = 22.dp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "G", style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = stringNamesSpacing)
                )
                Text(
                    text = "C", style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = stringNamesSpacing)
                )
                Text(
                    text = "E", style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = stringNamesSpacing)
                )
                Text(
                    text = "A", style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = stringNamesSpacing)
                )
            }

            //         UKULELE HANDLE        //

            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val fingers = currentChord.fingersToIntList()

                // BUTTONS
                for (i in 1 until 15) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = i.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )

                        Row {
                            for (j in 0 until 4) {
                                Box(
                                    modifier = Modifier.size(slotSize),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (fingers[j] == i) {
                                        Button(
                                            onClick = { fingers[j] = 0; viewModel.changeFingering(fingers); viewModel.resetChordSearched() },
                                            shape = CircleShape,
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.size(activeSize)
                                        ) {
                                            Text(i.toString(), maxLines = 1, softWrap = false)
                                        }
                                    } else {
                                        FilledTonalButton(
                                            onClick = { fingers[j] = i; viewModel.changeFingering(fingers); viewModel.resetChordSearched() },
                                            shape = CircleShape,
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.size(inactiveSize)
                                        ) {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}