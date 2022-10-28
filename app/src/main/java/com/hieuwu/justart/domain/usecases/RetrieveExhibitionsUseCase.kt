package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ExhibitionsDo


interface RetrieveExhibitionsUseCase :
    UseCase<RetrieveExhibitionsUseCase.Input, RetrieveExhibitionsUseCase.Result> {
    class Input
    sealed class Result {
        data class Success(val data: List<ExhibitionsDo>?) : Result()
        data class Failure(val error: Error?) : Result()
    }

    class Error
}