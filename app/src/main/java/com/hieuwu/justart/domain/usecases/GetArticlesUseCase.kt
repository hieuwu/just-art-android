package com.hieuwu.justart.domain.usecases

import com.hieuwu.justart.domain.models.ArticleDo

interface GetArticlesUseCase : UseCase<GetArticlesUseCase.Input, GetArticlesUseCase.Result> {
    class Input

    open class Result {
        class Success(val data: List<ArticleDo>) : GetArticlesUseCase.Result()
        object Failure : Result()
    }

}