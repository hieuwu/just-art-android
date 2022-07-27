package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorksResponse
import com.hieuwu.justartsdk.artworks.v1.dto.ArtWorksListDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrieveArtWorksUseCaseImpl @Inject constructor(
    private val artWorksService: ArtWorksService
) : RetrieveArtWorksUseCase {
    override suspend fun execute(input: RetrieveArtWorksUseCase.Input): RetrieveArtWorksUseCase.Result {
        val res: ApiResult<ArtWorksResponse>
        try {
            withContext(Dispatchers.IO) {
                res = artWorksService.getArtWorks(limit = 10)
            }
            return RetrieveArtWorksUseCase.Result.Success(res.response?.artWorks)
        } catch (e: Exception) {

        }
        return RetrieveArtWorksUseCase.Result.Success(null)
    }
}