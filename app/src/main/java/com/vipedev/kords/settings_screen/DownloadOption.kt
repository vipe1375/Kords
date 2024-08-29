package com.vipedev.kords.settings_screen

/*
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

 */