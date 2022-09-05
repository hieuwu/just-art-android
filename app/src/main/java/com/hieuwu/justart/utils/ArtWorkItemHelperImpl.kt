package com.hieuwu.justart.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.hieuwu.justart.BuildConfig
import com.hieuwu.justart.R
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsFragment
import com.hieuwu.justart.presentation.artworkdetails.ArtWorkDetailsFragmentArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ArtWorkItemHelperImpl(val context: Context) : ArtWorkItemHelper {
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
                context.startActivity(Intent.createChooser(intent, "Share artwork"))
            }
        }
    }

    override fun favouriteArtWork() {
        TODO("Not yet implemented")
    }

    override fun clickArtWork(
        itemView: View, title: View, image: View, card: View,
        navArg: Int
    ) {
        itemView.findNavController().navigate(
            R.id.action_artWorksFragment_to_artDetailsFragment,
            ArtWorkDetailsFragmentArgs(navArg).toBundle(),
            null,
            FragmentNavigatorExtras(
                title to ArtWorkDetailsFragment.TRANSITION_NAME_NAME,
                image to ArtWorkDetailsFragment.TRANSITION_NAME_IMAGE,
                card to ArtWorkDetailsFragment.TRANSITION_NAME_BACKGROUND
            )
        )
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