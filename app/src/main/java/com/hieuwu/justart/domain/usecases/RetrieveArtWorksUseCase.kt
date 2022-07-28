package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface RetrieveArtWorksUseCase :
    UseCase<RetrieveArtWorksUseCase.Input, RetrieveArtWorksUseCase.Result> {
    class Input()
    open class Result {
        data class Success(val data: List<ArtWorkDo>?) : Result()
        data class Failure(val error: Error?) : Result()
    }

    data class Error(val type: ErrorType)
    class ErrorType
}