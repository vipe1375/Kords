package com.vipedev.kords.songs_screen

import androidx.compose.runtime.Composable

@Composable
fun MainSongScreen(viewModel: SongsViewModel) {

    if (viewModel.isEditingSong) {
        if (viewModel.currentSong == null) {
            EditSongScreen(viewModel = viewModel)
        }
        else {
            EditSongScreen(viewModel = viewModel, song = viewModel.currentSong)
        }
    }

    else {
        if (viewModel.currentSong == null) {
            SongsListScreen(viewModel = viewModel)
        }
        else {
            DisplaySongScreen(viewModel = viewModel, song = viewModel.currentSong!!)
        }
    }

}