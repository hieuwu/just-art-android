package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArtWorkEntity

interface CheckFavoriteArtWorkExistedUseCase : UseCase<CheckFavoriteArtWorkExistedUseCase.Input,
        CheckFavoriteArtWorkExistedUseCase.Result> {
    data class Input(val artWorkId: Int)
    open class Result {
        data class Success(val result: ArtWorkEntity?) : Result()
        class Failure : Result()
    }
}