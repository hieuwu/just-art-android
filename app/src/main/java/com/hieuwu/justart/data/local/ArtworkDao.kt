package com.hieuwu.justart.data.local

import androidx.room.*
import com.hieuwu.justart.domain.models.ArtWorkEntity

@Dao
interface ArtworkDao {

//    @Query("SELECT * FROM favoritesTable")
//    suspend fun getAllFavoriteArtWorks(): List<ArtWorkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteArtwork(artwork: ArtWorkEntity)

    @Delete
    fun deleteFavoriteArtwork(artwork: ArtWorkEntity)

    @Query("SELECT * FROM favoritesTable WHERE id = :artworkId")
    fun getArtworkById(artworkId: Int): ArtWorkEntity?

}