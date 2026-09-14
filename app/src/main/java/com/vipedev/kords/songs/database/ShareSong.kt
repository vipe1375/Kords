package com.vipedev.kords.songs.database

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun shareSong(context: Context, song: Song) {
    val tempFile = File.createTempFile("temp_txt", ".txt", context.cacheDir)
    val text = "${song.title}-${song.artist}-${Converters.fromList(song.structure)}"
    println(text)
    tempFile.writeText(text) // Écrire le texte dans le fichier

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider", // Doit correspondre à l'attribut `authorities` dans le manifest
        tempFile
    )

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "text/plain"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Partager le texte"))
}