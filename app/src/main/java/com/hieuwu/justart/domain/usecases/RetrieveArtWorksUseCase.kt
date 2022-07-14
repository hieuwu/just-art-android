package com.hieuwu.justart.domain.usecases

interface RetrieveArtWorksUseCase :
    UseCase<RetrieveArtWorksUseCase.Input, RetrieveArtWorksUseCase.Result> {
    class Input
    class Result(
        val data: String?, val error: RetrieveArtWorksUseCase.Error?,
    )

    data class Error(val type: ErrorType)
    class ErrorType
}