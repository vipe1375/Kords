package com.vipedev.kords.chords.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import com.vipedev.kords.chords.ChordsViewModel
import com.vipedev.kords.songs.SongsViewModel

@Composable
fun PlayButton(
    isPlaying: Boolean,
    onPlay: () -> Unit
) {
    Button(
        onClick = onPlay,
        modifier = Modifier.padding(horizontal = 20.dp),
        enabled = !isPlaying,
        content = {
            if (isPlaying) {
                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                Text(stringResource(R.string.playing_chord_button_text))
            } else {
                Text(stringResource(R.string.play_chord_button_text))
            }
        }
    )
}
