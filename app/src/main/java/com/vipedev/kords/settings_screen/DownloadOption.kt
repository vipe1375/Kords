package com.vipedev.kords.settings_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import kotlinx.coroutines.launch


@Composable
fun DownloadOption(viewModel: SettingsViewModel, dataStore: StorePreferences) {

    val title = stringResource(R.string.update_chords_title)
    val autoDownload : Boolean = dataStore.getAutoDownload.collectAsState(initial = true).value!!

    val scope = rememberCoroutineScope()

    OutlinedButton(
        onClick = {
            scope.launch { viewModel.downloadChords() }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
    )
    {
        Text(text = title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = if (viewModel.isDownloading) {
                    MaterialTheme.colorScheme.scrim
                } else {
                    MaterialTheme.colorScheme.onPrimary
                }

        )
    }
}