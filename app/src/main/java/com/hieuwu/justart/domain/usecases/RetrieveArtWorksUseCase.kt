package com.hieuwu.justart.domain.usecases

interface RetrieveArtWorksUseCase :
    UseCase<RetrieveArtWorksUseCase.Input, RetrieveArtWorksUseCase.Result> {
    class Input
    open class Result {
        data class Success(val data: String?) : Result()
        data class Failure(val error: Error?) : Result()
    }

    data class Error(val type: ErrorType)
    class ErrorType
}