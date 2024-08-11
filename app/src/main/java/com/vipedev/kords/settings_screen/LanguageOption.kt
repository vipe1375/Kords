package com.vipedev.kords.settings_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vipedev.kords.R

class LanguageItem(
    val title: String,
    val id: String
)

@Composable
fun LanguageOption(viewModel: SettingsViewModel) {

    val title = stringResource(R.string.change_language_text)
    val enabled = true

    val languages = listOf(LanguageItem("Français", "fr"), LanguageItem("English", "en"))

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
            Text(text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .align(Alignment.CenterStart),

                color = when(enabled) {
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
                    languages.forEach { item ->
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
                                color = when(enabled) {
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