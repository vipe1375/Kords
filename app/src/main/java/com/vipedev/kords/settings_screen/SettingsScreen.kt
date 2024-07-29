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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R


@Composable
fun SettingsScreen(dataStore: StorePreferences, viewModel: SettingsViewModel) {

    val systemThemeOption = ToggleOptionItem(title = stringResource(R.string.use_system_theme),
        id = 0,
        description_on = stringResource(R.string.use_system_theme_on),
        description_off = stringResource(R.string.use_system_theme_off),
        enabled = true)

    val darkThemeOption = ToggleOptionItem(title = stringResource(R.string.use_dark_theme),
        id = 1,
        description_on = stringResource(R.string.use_dark_theme_on),
        description_off = stringResource(R.string.use_dark_theme_off),
        enabled = true )

    val languageOption = ListOptionItem(title = stringResource(R.string.change_language_text),
        description = "",
        enabled = true,
        listOf(LanguageItem("Français", "fr"), LanguageItem("English", "en"))
    )

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

            ToggleOption(viewModel = viewModel, dataStore = dataStore, option = systemThemeOption)

            ToggleOption(viewModel = viewModel, dataStore = dataStore, option = darkThemeOption)
            
            Spacer(modifier = Modifier.height(15.dp))

            ListOption(viewModel = viewModel, option = languageOption)
        }
    }
}