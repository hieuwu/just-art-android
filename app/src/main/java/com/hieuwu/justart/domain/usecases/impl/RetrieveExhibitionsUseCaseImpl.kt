package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.exhibitions.v1.ExhibitionsService
import com.hieuwu.justartsdk.exhibitions.v1.domain.ExhibitionsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RetrieveExhibitionsUseCaseImpl @Inject constructor(
    private val exhibitionService: ExhibitionsService
) : RetrieveExhibitionsUseCase {
    override suspend fun execute(input: RetrieveExhibitionsUseCase.Input): RetrieveExhibitionsUseCase.Result {
        val res: ApiResult<ExhibitionsResponse>
        try {
            withContext(Dispatchers.IO) {
                res = exhibitionService.getExhibitions()
            }
            return RetrieveExhibitionsUseCase.Result.Success(res.response?.data?.map { it.asDomainModel() })

        } catch (e: Exception) {
            Timber.e(e.message)
        }
        return RetrieveExhibitionsUseCase.Result.Success(null)
    }
}