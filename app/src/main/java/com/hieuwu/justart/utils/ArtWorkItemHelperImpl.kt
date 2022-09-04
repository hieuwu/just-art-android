package com.hieuwu.justart.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.hieuwu.justart.BuildConfig
import com.hieuwu.justart.domain.models.ArtWorkDo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ArtWorkItemHelperImpl: ArtWorkItemHelper {
    lateinit var context: Context

    override fun shareArtWork(artwork: ArtWorkDo) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        val file = File(context.externalCacheDir, File.separator + "artwork.jpg")
        val fout = FileOutputStream(file)
        coroutineScope.launch {
            val bitmap = getBitmapFromURL(artwork.imageUrl)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            fout.flush()
            fout.close()
            file.setReadable(true, false)
            val photoUri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                putExtra(Intent.EXTRA_TEXT, buildShareContent(artwork))

                type = "image/jpg"
                putExtra(Intent.EXTRA_STREAM, photoUri)
            }
            with(Dispatchers.Main) {
//                startActivity(Intent.createChooser(intent, "Share artwork"))
//                startActivity(Intent.createChooser(intent, "Share artwork"))
            }
        }
    }

    override fun favouriteArtWork() {
        TODO("Not yet implemented")
    }

    override fun clickArtWork() {
        TODO("Not yet implemented")
    }

    private fun getBitmapFromURL(src: String?): Bitmap? {
        var res: Bitmap? = null
        try {
            val url = URL(src)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            res = BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        return res
    }


    private fun buildShareContent(artwork: ArtWorkDo): String =
        "${artwork.title}, ${artwork.artistDisplay}, Art Institute of Chicago\n\nShared from " +
                "Just Art by @hieuwu, @dohonghuan"

}