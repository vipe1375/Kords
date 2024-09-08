package com.vipedev.kords.songs_screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

@Composable
fun SongsListScreen(viewModel: SongsViewModel) {

    val context = LocalContext.current

    val songs by viewModel.songs.observeAsState()

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        songs?.let {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 30.dp),
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                // title
                item {
                    Text(
                        text = stringResource(id = R.string.songs_nav_item),
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                // list of songs
                items(songs!!) { song ->
                    TextButton(
                        onClick = { viewModel.currentSong = song },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape
                    ) {
                        Box (
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            // Song infos column
                            Column (
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.align(Alignment.CenterStart)
                            ){
                                Text(
                                    text = song.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )

                                Text(
                                    text = song.artist,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Start,
                                    maxLines = 1
                                )
                            }

                            // Row for edit and delete icons
                            Row (
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                // edit icon
                                TextButton(
                                    onClick = {
                                        viewModel.isEditingSong = true
                                        viewModel.initCurrentSong(song)
                                    },
                                    //modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary)
                                }


                                // delete icon
                                TextButton(
                                    onClick = { viewModel.deleteSong(song = song, context = context) },
                                    //modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary)
                                }
                            }

                        }
                    }
                    /*
                    Row (
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Song infos column
                        Column (
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start,
                            //modifier = Modifier.align(Alignment.CenterStart)
                        ){
                            Text(
                                text = song.title,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )

                            Text(
                                text = song.artist,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Start,
                                maxLines = 1
                            )
                        }

                        // edit icon
                        TextButton(
                            onClick = {  },
                            //modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }


                        // delete icon
                        TextButton(
                            onClick = { viewModel.deleteSong(song = song, context = context) },
                            //modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(Icons.Default.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }

                    }*/
                }

                item {
                    Spacer(modifier = Modifier.height(128.dp))
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }
    }
}