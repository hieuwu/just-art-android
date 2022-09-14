package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkDo

interface CheckFavoriteArtWorkExistedUseCase : UseCase<CheckFavoriteArtWorkExistedUseCase.Input,
        CheckFavoriteArtWorkExistedUseCase.Result> {
    data class Input(val artWorkId: Int)
    open class Result {
        data class Success(val result: Boolean) : Result()
        class Failure : Result()
    }
}