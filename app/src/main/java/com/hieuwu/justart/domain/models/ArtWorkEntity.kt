package com.hieuwu.justart.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritesTable")
data class ArtWorkEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "artistDisplay")
    val artistDisplay: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null
)