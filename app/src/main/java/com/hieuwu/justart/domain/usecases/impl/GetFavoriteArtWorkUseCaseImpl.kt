package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkEntity
import com.hieuwu.justart.domain.usecases.GetFavoriteArtWorkUseCase
import com.hieuwu.justart.mapper.entityToDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavoriteArtWorkUseCaseImpl @Inject constructor(
    private val artworkRepository: ArtworkRepository
) : GetFavoriteArtWorkUseCase {
    override suspend fun execute(input: GetFavoriteArtWorkUseCase.Input): GetFavoriteArtWorkUseCase.Result {
        val res: List<ArtWorkEntity>
        withContext(Dispatchers.IO) {
            res = artworkRepository.getAllFavoriteArtwork()
        }
        return GetFavoriteArtWorkUseCase.Result.Success(res.entityToDomainModel())
    }
}