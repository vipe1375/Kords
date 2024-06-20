package com.example.kordsjetpack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SearchByID(viewModel: ChordsViewModel) {
    val activeButtonSize: Dp = 66.dp
    val inactiveButtonSize: Dp = 60.dp
    val buttonPadding: Dp = 5.dp

    var inputChord by remember {
        mutableStateOf("")
    }

    val currentChord = viewModel.currentChord


    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //        SEARCH TEXT       //

            Text(
                text = "Find a chord's name",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            val currentChordName: String = viewModel.currentChordName
            Text(
                text = "Current chord : $currentChordName",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

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
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // BUTTONS
                for (i in 1 until 15) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = i.toString(),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Row {
                            for (j in 0 until 4) {
                                if (currentChord[j].toString() == i.toString()) {
                                    Button(
                                        onClick =
                                        {
                                            inputChord = when (j) {
                                                0 -> "0" + currentChord.substring(1)
                                                1 -> currentChord.substring(
                                                    0,
                                                    1
                                                ) + "0" + currentChord.substring(2, 4)

                                                2 -> currentChord.substring(
                                                    0,
                                                    2
                                                ) + "0" + currentChord.substring(3, 4)

                                                3 -> currentChord.substring(0, 3) + "0"
                                                else -> currentChord
                                            }; viewModel.changeCurrentChord(inputChord)
                                        },
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(activeButtonSize)
                                            .padding(buttonPadding)
                                    ) {
                                    }
                                } else {
                                    FilledTonalButton(
                                        onClick =
                                        {
                                            inputChord = when (j) {
                                                0 -> i.toString() + currentChord.substring(1)
                                                1 -> currentChord.substring(
                                                    0,
                                                    1
                                                ) + i.toString() + currentChord.substring(2, 4)

                                                2 -> currentChord.substring(
                                                    0,
                                                    2
                                                ) + i.toString() + currentChord.substring(3, 4)

                                                3 -> currentChord.substring(0, 3) + i.toString()
                                                else -> currentChord
                                            }; viewModel.changeCurrentChord(inputChord)
                                        },
                                        shape = CircleShape,
                                        modifier = Modifier
                                            .size(inactiveButtonSize)
                                            .padding(buttonPadding)
                                    ) {
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