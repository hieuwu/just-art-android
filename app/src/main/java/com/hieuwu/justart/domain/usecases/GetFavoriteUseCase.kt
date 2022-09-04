package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface GetFavoriteUseCase {
    suspend fun saveFavoriteArtwork(artWork: ArtWorkDo)
    suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo)
    suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean
}