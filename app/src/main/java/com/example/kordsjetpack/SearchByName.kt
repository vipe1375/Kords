package com.example.kordsjetpack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchByName(viewModel: ChordsViewModel) {

    val focusManager = LocalFocusManager.current

    Surface (
        //color = MaterialTheme.colorScheme.background
    ) {
        Column (
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
                        viewModel.chordSearched,
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
                    var matchingChords = viewModel.chordsList.chords.filter { it.doesMatchQuery(viewModel.chordSearched) }
                    if (matchingChords.size > 3) {
                        matchingChords = matchingChords.subList(0, 3)
                    }
                    if (viewModel.chordSearched.isNotEmpty() && !viewModel.searched) {

                        LazyColumn (
                            modifier = Modifier.padding(horizontal = 30.dp)
                        ){
                            items(matchingChords) { c ->
                                ListButton(text = c.displayName, viewModel = viewModel, focusManager)
                            }
                        }
                    }
                }

                // search button
                Button(onClick = {viewModel.searchChord()},
                    Modifier.padding(top = 10.dp),
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.loupe3),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
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



                if (viewModel.chordFound) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        viewModel.visualizeChord(viewModel.chordSearchedID)
                        focusManager.clearFocus()
                    },
                        content = {
                            Text("Visualize")
                        })
                }

            }
        }
    }
}

@Composable
fun ListButton(text: String, viewModel: ChordsViewModel, focusManager: FocusManager) {

    OutlinedButton(onClick = { viewModel.changeChordSearched(text)
                             viewModel.searchChord()
                             focusManager.clearFocus()},
        content = { Text(text = text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)},
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.padding(0.dp).size(width = 140.dp, height = 50.dp)
    ) 
    
}