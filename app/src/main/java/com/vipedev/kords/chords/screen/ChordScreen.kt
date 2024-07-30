package com.vipedev.kords.chords.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

@Composable
fun ChordScreen(viewModel: ChordsViewModel) {

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        // SEARCH BY NAME SURFACE

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))


            //          SEARCH BAR          //
            SearchByName(viewModel)


            //          SEPARATOR         //
            Separator(text = stringResource(id = R.string.or_spacer))
            Spacer(modifier = Modifier.height(12.dp))


            //          SEARCH A CHORD BY ID SURFACE         //
            SearchByID(viewModel)

        }
    }
}

@Composable
fun Separator(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.barre3),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(width = 100.dp, height = 8.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary
        )

        Icon(
            painter = painterResource(id = R.drawable.barre3),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.size(width = 100.dp, height = 8.dp)
        )
    }
}




