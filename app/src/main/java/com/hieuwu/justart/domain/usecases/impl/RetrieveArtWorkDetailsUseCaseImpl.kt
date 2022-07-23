package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorkDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RetrieveArtWorkDetailsUseCaseImpl @Inject constructor(
    private val artWorksService: ArtWorksService
) : RetrieveArtWorkDetailsUseCase {
    override suspend fun execute(input: RetrieveArtWorkDetailsUseCase.Input): RetrieveArtWorkDetailsUseCase.Result {
        val res: ApiResult<ArtWorkDetailsResponse>
        try {
            withContext(Dispatchers.IO) {
                res = artWorksService.getArtWorkDetails(input.id)
            }
            return RetrieveArtWorkDetailsUseCase.Result.Success(res.response?.artWorkDetails?.asDomainModel())
        } catch (e: Exception) {
            Timber.e(e)
        }
        return RetrieveArtWorkDetailsUseCase.Result.Failure(
            RetrieveArtWorkDetailsUseCase.Error(
                RetrieveArtWorkDetailsUseCase.ErrorType.GENERIC
            )
        )
    }
}