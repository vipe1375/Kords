package com.vipedev.kords.songs_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

@Composable
fun SongsScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(50.dp))

            Text(text = stringResource(id = R.string.contact_nav_item),
                modifier = Modifier.padding(20.dp))



            Text(text = stringResource(R.string.contact_twitter),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(10.dp))

            Text(text = stringResource(R.string.contact_discord),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(10.dp))
        }
    }
}