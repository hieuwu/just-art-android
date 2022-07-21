package com.hieuwu.justart.domain.usecases

import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork
import com.hieuwu.justartsdk.artworks.v1.dto.ArtWorksListDto

interface RetrieveArtWorksUseCase :
    UseCase<RetrieveArtWorksUseCase.Input, RetrieveArtWorksUseCase.Result> {
    class Input()
    open class Result {
        data class Success(val data: List<ArtWork>?) : Result()
        data class Failure(val error: Error?) : Result()
    }

    data class Error(val type: ErrorType)
    class ErrorType
}