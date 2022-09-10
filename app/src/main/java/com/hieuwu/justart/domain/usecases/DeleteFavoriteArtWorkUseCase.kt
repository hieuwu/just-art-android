package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface DeleteFavoriteArtWorkUseCase : UseCase<DeleteFavoriteArtWorkUseCase.Input,
        DeleteFavoriteArtWorkUseCase.Result> {
    data class Input(val artWork: ArtWorkDo)
    open class Result {
        class Success : Result()
        class Failure : Result()
    }
}