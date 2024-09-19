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

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
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
            horizontalAlignment = Alignment.Start

        ) {
            Text(text = stringResource(id = R.string.settings_nav_item),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())

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


            //      CONTACT      //
            Text(text = stringResource(R.string.contact_options_header),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Row (
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Email,
                    modifier = Modifier.size(40.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary)

                Text(text = "vipe1375@disroot.org",
                    modifier = Modifier.padding(8.dp),
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodySmall,
                    //modifier = Modifier.padding(top = 20.dp)
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onPrimary)
            }

            Text(text = stringResource(R.string.contact_issues),
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.titleMedium,
                //modifier = Modifier.padding(top = 20.dp)
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary)

            Spacer(modifier = Modifier.height(20.dp))

            //       SOURCE CODE      //
            Text(text = stringResource(R.string.sourcecode_options_header),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Column {
                val context = LocalContext.current
                val gitlabIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://gitlab.com/vipe1375/kords")) }
                val githubIntent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vipe1375/kords")) }

                //Gitlab
                Row (
                    modifier = Modifier.padding(10.dp)
                ){
                    Icon(painter = painterResource(R.drawable.gitlab_logo),
                        modifier = Modifier
                            .size(40.dp),
                        contentDescription = "gitlab logo",
                        tint = Color.Unspecified)

                    TextButton(
                        onClick = { context.startActivity(gitlabIntent) },
                        //modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text(text = stringResource(R.string.sourcecode_gitlab),
                            style = MaterialTheme.typography.titleMedium,
                            //modifier = Modifier.padding(top = 20.dp)
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary)

                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }


                // Github
                Row (
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.github_logo),
                        modifier = Modifier
                            .size(40.dp),
                        contentDescription = "github logo",
                        tint = MaterialTheme.colorScheme.onPrimary)

                    TextButton(onClick = { context.startActivity(githubIntent) },
                        //modifier = Modifier.padding(top = 20.dp)
                    ) {
                        Text(text = stringResource(R.string.sourcecode_github),
                            //modifier = Modifier.padding(top = 20.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onPrimary)

                        Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            tint = MaterialTheme.colorScheme.onPrimary)
                    }
                }

                
            }
        }
    }
}