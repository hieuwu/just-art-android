package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface CheckFavoriteArtWorkExistedUseCase : UseCase<CheckFavoriteArtWorkExistedUseCase.Input,
        CheckFavoriteArtWorkExistedUseCase.Result> {
    data class Input(val artWork: ArtWorkDo)
    open class Result {
        class Success(result: Boolean) : Result()
        class Failure : Result()
    }
}