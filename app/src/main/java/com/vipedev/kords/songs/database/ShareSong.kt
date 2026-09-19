package com.vipedev.kords.songs.database

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun shareSong(context: Context, song: Song) {
    val tempFile = File.createTempFile("song_${song.id}", ".kords", context.cacheDir)
    val text = song.toJson()
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

fun importSongFromTxt(context: Context, uri: Uri): Song? = try {
    val content = context.contentResolver.openInputStream(uri)
        ?.bufferedReader()?.use { it.readText() }?.trim()
    if (content.isNullOrBlank()) null else Song.fromJson(content)
} catch (e: Exception) {
    e.printStackTrace()
    null // JSON invalide ou champs manquants
}