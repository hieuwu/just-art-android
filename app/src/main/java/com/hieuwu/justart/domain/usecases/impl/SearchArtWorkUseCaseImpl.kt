package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchArtWorkUseCaseImpl @Inject constructor(
    private val artWorksService: ArtWorksService,
) : SearchArtWorkUseCase {
    override suspend fun execute(input: SearchArtWorkUseCase.Input): SearchArtWorkUseCase.Result {
        var res: ApiResult<ArtWorksResponse>? = null
        try {
            withContext(Dispatchers.IO) {
                val ids = mutableListOf<Int>()
                val listRes = artWorksService.searchArtWorks(input.query).response?.data
                listRes?.forEach { it -> it.id?.let { it1 -> ids.add(it1) } }
                withContext(Dispatchers.IO) {
                    res = artWorksService.getArtWorks(ids.joinToString(","))
                }
            }
            handleError(res)
            return SearchArtWorkUseCase.Result.Success(res?.response?.artWorks?.asDomainModel())
        } catch (e: Exception) {

        }
        return SearchArtWorkUseCase.Result.Failure(
            SearchArtWorkUseCase.Error(
                SearchArtWorkUseCase.ErrorType.GENERIC
            )
        )
    }

    private fun handleError(res: ApiResult<ArtWorksResponse>?): SearchArtWorkUseCase.Result.Failure {
        if (res == null) {
            return SearchArtWorkUseCase.Result.Failure(
                SearchArtWorkUseCase.Error(
                    SearchArtWorkUseCase.ErrorType.GENERIC
                )
            )
        } else if (res.response?.artWorks.isNullOrEmpty()) {
            return SearchArtWorkUseCase.Result.Failure(
                SearchArtWorkUseCase.Error(
                    SearchArtWorkUseCase.ErrorType.EMPTY
                )
            )
        }
        return SearchArtWorkUseCase.Result.Failure()
    }
}