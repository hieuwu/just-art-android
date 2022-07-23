package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDetailsDo

interface RetrieveArtWorkDetailsUseCase: UseCase<RetrieveArtWorkDetailsUseCase.Input, RetrieveArtWorkDetailsUseCase.Result>  {
    data class Input(val id: Int)
    open class Result {
        data class Success(val data: ArtWorkDetailsDo?) : Result()
        data class Failure(val error: RetrieveArtWorkDetailsUseCase.Error?) : Result()
    }

    data class Error(val type: ErrorType)
    enum class ErrorType {
        GENERIC
    }
}