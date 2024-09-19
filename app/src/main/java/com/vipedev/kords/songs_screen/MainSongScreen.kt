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