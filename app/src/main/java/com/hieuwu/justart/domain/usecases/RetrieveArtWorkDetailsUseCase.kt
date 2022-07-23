package com.hieuwu.justart.domain.usecases

import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork

interface RetreiveArtWorkDetailsUseCase: UseCase<RetreiveArtWorkDetailsUseCase.Input, RetreiveArtWorkDetailsUseCase.Result>  {
    class Input()
    open class Result {
        data class Success(val data: List<ArtWork>?) : Result()
        data class Failure(val error: RetreiveArtWorkDetailsUseCase.Error?) : Result()
    }

    data class Error(val type: ErrorType)
    class ErrorType
}