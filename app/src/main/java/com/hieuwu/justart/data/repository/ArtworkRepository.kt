package com.hieuwu.justart.data.repository

import com.hieuwu.justart.domain.models.ArtWorkDo

interface ArtworkRepository {
    suspend fun saveFavoriteArtwork(artwork: ArtWorkDo)
    suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo)
    suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean
}