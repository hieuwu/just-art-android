package com.hieuwu.justart.di

import android.content.Context
import androidx.room.Room
import com.hieuwu.justart.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "artwork_database.db"
    ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideArtworkDao(db: AppDatabase) = db.artworkDao()
}