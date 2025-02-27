package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkEntity
import com.hieuwu.justart.domain.usecases.CheckFavoriteArtWorkExistedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckFavoriteArtWorkExistedUseCaseImpl @Inject constructor(
    private val artworkRepository: ArtworkRepository
) : CheckFavoriteArtWorkExistedUseCase {
    override suspend fun execute(input: CheckFavoriteArtWorkExistedUseCase.Input): CheckFavoriteArtWorkExistedUseCase.Result {
        var res: ArtWorkEntity?
        withContext(Dispatchers.IO) {
            res = artworkRepository.getArtworkFavorite(input.artWorkId)
        }
        return CheckFavoriteArtWorkExistedUseCase.Result.Success(result = res)
    }
}