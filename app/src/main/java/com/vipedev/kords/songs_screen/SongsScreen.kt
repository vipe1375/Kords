package com.vipedev.kords.songs_screen

import androidx.compose.runtime.Composable

@Composable
fun SongsScreen(viewModel: SongsViewModel) {

    if (viewModel.isCreatingSong) {
        NewSongScreen(viewModel = viewModel)
    }

    else {
        SongsListScreen(viewModel = viewModel)
    }
}