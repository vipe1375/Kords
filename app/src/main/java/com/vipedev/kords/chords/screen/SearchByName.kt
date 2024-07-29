package com.vipedev.kords.chords.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

@Composable
fun SearchByName(viewModel: ChordsViewModel) {

    val focusManager = LocalFocusManager.current

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
                        onValueChange = { viewModel.changeChordSearched(it) },
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

                    var matchingChords = viewModel.getSuggestions()
                    println(matchingChords)
                    if (matchingChords.size > 3) {
                        matchingChords = matchingChords.subList(0, 3)
                    }

                    if (viewModel.chordSearched.isNotEmpty() && !viewModel.searched) {

                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 30.dp)
                        ) {
                            items(matchingChords) { c ->
                                ListButton(text = c.name, viewModel = viewModel, focusManager = focusManager)
                            }
                        }
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


                        Button(modifier = Modifier
                                .padding(horizontal = 20.dp),
                            onClick = {
                                if (nbResults > 1) {
                                    val visualizedChord = viewModel.searchResult[viewModel.visualizedID - 1]
                                    viewModel.visualizeChord(visualizedChord)

                                }
                                else {
                                    viewModel.visualizeChord(viewModel.searchResult[0])

                                }
                                //focusManager.clearFocus()

                            },
                            content = {
                                Text(LocalContext.current.resources.getQuantityString(R.plurals.visualize_button_text,
                                    viewModel.searchResult.size,   // quantity
                                    viewModel.visualizedID,   // arg1
                                    viewModel.searchResult.size))  // arg2
                            })

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
fun ListButton(text: String, viewModel: ChordsViewModel, focusManager: FocusManager) {
    FilledTonalButton(
        onClick = {
            viewModel.changeChordSearched(text)
            viewModel.searchChord()
            focusManager.clearFocus()
        },
        shape = RectangleShape,
        modifier = Modifier
            .width(140.dp)
            .padding(0.dp)
    )
    {
        Text(text = text)
    }

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
