package com.hieuwu.justart.utils

import android.content.Context
import android.view.View
import com.hieuwu.justart.domain.models.ArtWorkDo

interface ArtWorkItemHelper {
    fun shareArtWork(artwork: ArtWorkDo)
    fun favouriteArtWork()
    fun clickArtWork(itemView: View, title: View, image: View, card: View,
                     navArg: Int)
}

object ArtWorkItemHelperFactory {
    fun create(context: Context): ArtWorkItemHelper {
        return ArtWorkItemHelperImpl(context)
    }
}