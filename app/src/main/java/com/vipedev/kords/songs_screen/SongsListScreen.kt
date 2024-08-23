package com.vipedev.kords.songs_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.vipedev.kords.R
import kotlinx.coroutines.launch
import com.vipedev.kords.songs_screen.database.Song

@Composable
fun SongsListScreen(viewModel: SongsViewModel) {

    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(50.dp))

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
            items(viewModel.getSavedSongs().toMutableList()) { song ->
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape
                ) {
                    Box (
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
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

                        // three dots icon
                        TextButton(
                            onClick = { viewModel.deleteSong(song = song, context = context) },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(Icons.Default.MoreVert,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(128.dp))
            }
        }




        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }
    }
}