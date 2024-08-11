package com.vipedev.kords.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R


@Composable
fun SettingsScreen(dataStore: StorePreferences, viewModel: SettingsViewModel) {

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(text = stringResource(id = R.string.settings_nav_item), textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(50.dp))

            // Appearance
            Text(text = stringResource(R.string.appearance_options_header),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            UseSystemThemeOption(viewModel = viewModel, dataStore = dataStore)

            UseDarkThemeOption(viewModel = viewModel, dataStore = dataStore)
            
            Spacer(modifier = Modifier.height(20.dp))

            // Language
            Text(text = stringResource(R.string.language_options_header),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            LanguageOption(viewModel = viewModel)

            Spacer(modifier = Modifier.height(20.dp))
/*
            // Updates
            Text(text = stringResource(R.string.auto_download_options_header),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            AutoDownloadOption(dataStore = dataStore)

            if (!dataStore.getAutoDownload.collectAsState(initial = true).value!!) {
                DownloadOption(viewModel = viewModel, dataStore = dataStore)
            }*/
        }
    }
}