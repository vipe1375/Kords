package com.vipedev.kords.settings_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R
import kotlinx.coroutines.launch

@Composable
fun AutoDownloadOption(dataStore: StorePreferences) {

    val title = stringResource(R.string.auto_download)
    val descriptionOn = stringResource(R.string.auto_download_on)
    val descriptionOff = stringResource(R.string.auto_download_off)

    val frac = 0.8F

    // scope
    val scope = rememberCoroutineScope()

    val savedState = dataStore.getAutoDownload.collectAsState(initial = true).value!!

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){

        Box (
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth(frac)
            )

            Switch(
                checked = savedState,
                onCheckedChange = {
                    scope.launch { dataStore.saveAutoDownload(!savedState) }
                },
                enabled = true,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .scale(0.9f)
            )
        }

        Text(
            text = if (savedState) {
                descriptionOn
            } else {
                descriptionOff
            },
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.fillMaxWidth(frac)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}