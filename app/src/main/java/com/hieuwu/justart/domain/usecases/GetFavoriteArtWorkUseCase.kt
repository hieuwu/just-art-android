package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface GetFavoriteArtWorkUseCase : UseCase<GetFavoriteArtWorkUseCase.Input,
        GetFavoriteArtWorkUseCase.Result> {
    class Input()
    open class Result {
        data class Success(val data: List<ArtWorkDo>?) : Result()
        class Failure : Result()
    }
}