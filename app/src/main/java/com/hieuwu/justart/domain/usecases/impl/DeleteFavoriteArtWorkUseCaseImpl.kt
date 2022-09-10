package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.usecases.DeleteFavoriteArtWorkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFavoriteArtWorkUseCaseImpl @Inject constructor(
    private val artworkRepository: ArtworkRepository
) : DeleteFavoriteArtWorkUseCase {
    override suspend fun execute(input: DeleteFavoriteArtWorkUseCase.Input): DeleteFavoriteArtWorkUseCase.Result {
        withContext(Dispatchers.IO) {
            artworkRepository.deleteFavoriteArtwork(input.artWork)
        }
        return DeleteFavoriteArtWorkUseCase.Result.Success()
    }
}