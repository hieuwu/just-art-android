package com.hieuwu.justart.data.repository

import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.models.ArtWorkEntity

interface ArtworkRepository {
    suspend fun getAllFavoriteArtwork(): List<ArtWorkEntity>
    suspend fun saveFavoriteArtwork(artwork: ArtWorkDo)
    suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo)
    suspend fun isArtworkFavorite(artworkId: Int): Boolean
}