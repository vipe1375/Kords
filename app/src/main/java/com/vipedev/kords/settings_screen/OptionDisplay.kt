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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ToggleOption(viewModel: SettingsViewModel, dataStore: StorePreferences, option : ToggleOptionItem) {

    // scope
    val scope = rememberCoroutineScope()

    val useSystemTheme = dataStore.getUseSystemTheme.collectAsState(initial = true).value!!

    val savedState = if (option.id==0) {
        dataStore.getUseSystemTheme.collectAsState(initial = true).value!!
    } else {
        dataStore.getUseDarkTheme.collectAsState(initial = true).value!!
    }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ){

        Box (
            contentAlignment = Alignment.CenterEnd
        ){
            Column (
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
            ){
                Text(text = option.title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = when(useSystemTheme && option.id == 1) {
                        false -> MaterialTheme.colorScheme.onPrimary
                        true -> MaterialTheme.colorScheme.scrim}
                )

                Text(text =
                        if (savedState) {
                            option.description_on
                        } else {
                                option.description_off
                        },
                    style = MaterialTheme.typography.labelMedium,
                    color = when(useSystemTheme && option.id == 1) {
                        false -> MaterialTheme.colorScheme.onPrimary
                        true -> MaterialTheme.colorScheme.scrim}
                )
            }


            Switch(
                checked = savedState,
                onCheckedChange = {
                    if (option.id == 0) {
                        scope.launch { dataStore.saveUseSystemTheme(!savedState) }
                    } else {
                        scope.launch { dataStore.saveUseDarkTheme(!savedState) }
                    }
                },
                enabled = !(useSystemTheme && option.id == 1)
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ListOption(viewModel: SettingsViewModel, option: ListOptionItem) {
    var newLocale by rememberSaveable {
        mutableStateOf("")
    }

    Column (
        horizontalAlignment = Alignment.Start
    ){
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = true) {
                    viewModel.changeDropdownState(true)
                },
            contentAlignment = Alignment.CenterEnd
        ){
            Text(text = option.title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .align(Alignment.CenterStart),

                color = when(option.enabled) {
                    true -> MaterialTheme.colorScheme.onPrimary
                    false -> MaterialTheme.colorScheme.scrim}
            )


            // Button for dropdown
            TextButton(onClick = { viewModel.changeDropdownState(true) },
                shape = RectangleShape,
                modifier = Modifier.width(130.dp))
            {

                // Text of current language
                Text(text = viewModel.getLocaleName(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelLarge)

                // dropdown menu
                DropdownMenu(expanded = viewModel.isDropdownVisible,
                    onDismissRequest = { viewModel.changeDropdownState(false) },
                    modifier = Modifier,
                    offset = DpOffset(0.dp, 10.dp)
                ) {

                    // dropdown items
                    option.items.forEach { item ->
                        TextButton(
                            onClick = {
                                viewModel.changeDropdownState(false)
                                newLocale = item.id
                            }
                        ) {
                            Text(text = item.title,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                                color = when(option.enabled) {
                                    true -> MaterialTheme.colorScheme.onPrimary
                                    false -> MaterialTheme.colorScheme.scrim}
                            )
                        }
                    }
                }
            }
        }
    }
    viewModel.localeSelection(LocalContext.current, newLocale)

    Spacer(modifier = Modifier.height(10.dp))
}