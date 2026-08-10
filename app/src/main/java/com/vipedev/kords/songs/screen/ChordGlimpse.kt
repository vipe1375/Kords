package com.vipedev.kords.songs.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vipedev.kords.chords.database.findChord
import com.vipedev.kords.songs.SongsViewModel
import com.vipedev.kords.R
import com.vipedev.kords.chords.database.Chord
import com.vipedev.kords.chords.screen.PlayButton
import com.vipedev.kords.chords.screen.VisualizeButton


@Composable
fun ChordGlimpse(viewModel: SongsViewModel) {
    /*
    Creates a dialog to show the fingers of a chord.
    */

    // stop player when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.player.stop()
        }
    }

    val chordName = viewModel.chordInDialogName

    if (chordName.isBlank()) {
        return;
    }

    var text: String;
    val result = findChord(chordName.lowercase())
    val isChord = result.isNotEmpty()
    text = if (!isChord) {
        stringResource(R.string.no_chord_found_name)
    }
    else {
        result[viewModel.chordDialogResultId].fingers
    }

    fun onDismissRequest () {
        viewModel.showChordDialog = false
        viewModel.chordDialogResultId = 0
        viewModel.chordInDialogName = ""
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.height(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // title
                    Text(
                        text = Chord().renderName(chordName),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    TextButton(
                        onClick = {
                            onDismissRequest()
                        },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Fermer")
                    }
                }


                if (isChord) {
                    ChordDiagram(result[viewModel.chordDialogResultId])
                }

                // bottom buttons
                if (isChord) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        VisualizeButton(
                            result.size > 1,
                            result.size,
                            viewModel.chordDialogResultId,
                            modifier = Modifier,
                            onClick = {
                                viewModel.chordDialogResultId =
                                    (viewModel.chordDialogResultId + 1) % result.size
                            }
                        )

                        PlayButton(
                            viewModel.player.isPlaying,
                            modifier = Modifier,
                            onPlay = { viewModel.playChord(result[viewModel.chordDialogResultId]) }
                        )


                    }
                }
            }
        }
    }
}

@Composable
fun ChordDiagram(chord: Chord, modifier: Modifier = Modifier) {
    val fingers = chord.fingersToIntList()
    val fretted = fingers.filter { it > 0 }
    val minFret = fretted.minOrNull() ?: 1
    val maxFret = fretted.maxOrNull() ?: 1
    val fretCount = 4

    // fenêtre glissante : cadrée sur les doigtés, mais reste en bas si l'accord est bas
    val startFret = when {
        fretted.isEmpty() -> 1
        maxFret <= fretCount -> 1
        else -> minFret
    }

    val strings = listOf("G", "C", "E", "A")
    val cell = 40.dp
    val circle = 30.dp

    Column(
        modifier = modifier.padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // marqueurs cordes à vide

        if (fingers.contains(0)) {
            Row {
                fingers.forEach { f ->
                    Box(Modifier.size(cell), contentAlignment = Alignment.Center) {
                        if (f == 0) Text("○")
                    }
                }
            }
        }

        // noms des cordes
        Row {
            strings.forEach {
                Box(Modifier.size(cell), contentAlignment = Alignment.Center) {
                    Text(it, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        // grille des frettes
        for (fret in startFret until startFret + fretCount) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text (
                    "$fret",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(end = 10.dp))
                fingers.forEach { f ->
                    Box(Modifier.size(cell), contentAlignment = Alignment.Center) {
                        if (f == fret) {
                            Box(
                                Modifier
                                    .size(circle)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    fret.toString(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}