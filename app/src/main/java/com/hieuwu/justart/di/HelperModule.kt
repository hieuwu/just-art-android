package com.hieuwu.justart.di

import android.content.Context
import com.hieuwu.justart.utils.ArtWorkItemHelper
import com.hieuwu.justart.utils.ArtWorkItemHelperFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(FragmentComponent::class)
@Module
class HelperModule {
    @Provides
    fun provideArtworkItemHelper(@ActivityContext context: Context): ArtWorkItemHelper {
        return ArtWorkItemHelperFactory.create(context)
    }
}