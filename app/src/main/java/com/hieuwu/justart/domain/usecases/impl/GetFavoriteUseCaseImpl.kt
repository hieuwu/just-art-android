package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.GetFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoriteUseCaseImpl @Inject constructor(
    private val artworkRepository: ArtworkRepository
) : GetFavoriteUseCase {

    override suspend fun saveFavoriteArtwork(artWork: ArtWorkDo) {
        withContext(Dispatchers.IO) {
            artworkRepository.saveFavoriteArtwork(artWork)
        }
    }

    override suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo) {
        withContext(Dispatchers.IO) {
            artworkRepository.deleteFavoriteArtwork(artwork)
        }
    }

    override suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean {
        var res = false
        withContext(Dispatchers.IO) {
            res = artworkRepository.isArtworkFavorite(artwork)
        }
        return res
    }
}