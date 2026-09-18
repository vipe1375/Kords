package com.vipedev.kords.songs.database

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun shareSong(context: Context, song: Song) {
    val tempFile = File.createTempFile("song_${song.id}", ".kords", context.cacheDir)
    val text = "${song.title}-${song.artist}-${Converters.fromList(song.structure)}"
    tempFile.writeText(text)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        tempFile
    )

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "application/kords"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Partager la chanson"))
}

fun importSongFromTxt(context: Context, uri: Uri): Song? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val fileContent = reader.use { it.readText() }.trim()

        if (fileContent.isBlank()) {
            return null
        }

        val parts = fileContent.split("-", limit = 3)
        if (parts.size != 3) {
            return null // Format invalide
        }

        val title = parts[0]
        val artist = parts[1]
        val structureString = parts[2]

        val structure = Converters.fromString(structureString)

        Song(title = title, artist = artist, structure = structure)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
