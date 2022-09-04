package com.hieuwu.justart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hieuwu.justart.domain.models.ArtWorkEntity

@Database(entities = [ArtWorkEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao
}