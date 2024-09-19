/*
 * Kords
 * Copyright (C) 2024 Victor Pezennec--Deutsch
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.vipedev.kords.settings_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import kotlinx.coroutines.launch

@Composable
fun UseDarkThemeOption(viewModel: SettingsViewModel, dataStore: StorePreferences) {

    val title = stringResource(R.string.use_dark_theme)
    val descriptionOn = stringResource(R.string.use_dark_theme_on)
    val descriptionOff = stringResource(R.string.use_dark_theme_off)

    // scope
    val scope = rememberCoroutineScope()

    val useSystemTheme = dataStore.getUseSystemTheme.collectAsState(initial = true).value!!

    val savedState = dataStore.getUseDarkTheme.collectAsState(initial = true).value!!


    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){

        Box (
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ){

            Text(text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 5.dp),
                color = when(useSystemTheme) {
                    false -> MaterialTheme.colorScheme.onPrimary
                    true -> MaterialTheme.colorScheme.scrim}
            )

            Switch(
                checked = savedState,
                onCheckedChange = {
                    scope.launch { dataStore.saveUseDarkTheme(!savedState) }
                },
                enabled = !(useSystemTheme),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .scale(0.9f)
            )
        }

        Text(text =
                if (savedState) {
                    descriptionOn
                } else {
                    descriptionOff
                },
            style = MaterialTheme.typography.labelMedium,
            color = when(useSystemTheme) {
                false -> MaterialTheme.colorScheme.onPrimary
                true -> MaterialTheme.colorScheme.scrim}
            )
    }

    Spacer(modifier = Modifier.height(10.dp))
}