package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface SearchArtWorkUseCase : UseCase<SearchArtWorkUseCase.Input,
        SearchArtWorkUseCase.Result> {
    data class Input(val query: String)
    open class Result {
        data class Success(val data: List<ArtWorkDo>?) : Result()
        data class Failure(val error: Error? = null) : Result()
    }

    data class Error(val type: ErrorType)
    enum class ErrorType {
        GENERIC,
        EMPTY
    }
}