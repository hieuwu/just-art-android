package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface SaveFavoriteArtWorkUseCase : UseCase<SaveFavoriteArtWorkUseCase.Input,
        SaveFavoriteArtWorkUseCase.Result> {
    data class Input(val artWork: ArtWorkDo)
    open class Result {
        class Success : Result()
        class Failure : Result()
    }
}