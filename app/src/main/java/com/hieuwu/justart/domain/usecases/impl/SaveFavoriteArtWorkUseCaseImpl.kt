package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.usecases.SaveFavoriteArtWorkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFavoriteArtWorkUseCaseImpl @Inject constructor(
    private val artworkRepository: ArtworkRepository
) : SaveFavoriteArtWorkUseCase {
    override suspend fun execute(input: SaveFavoriteArtWorkUseCase.Input): SaveFavoriteArtWorkUseCase.Result {
        withContext(Dispatchers.IO) {
            artworkRepository.saveFavoriteArtwork(input.artWork)
        }
        return SaveFavoriteArtWorkUseCase.Result.Success()
    }
}