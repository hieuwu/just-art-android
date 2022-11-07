package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.GetArticlesUseCase
import com.hieuwu.justartsdk.articles.v1.ArticlesService
import javax.inject.Inject

class GetArticlesUseCaseImpl @Inject constructor(
    private val articlesService: ArticlesService
) : GetArticlesUseCase {
    override suspend fun execute(input: GetArticlesUseCase.Input): GetArticlesUseCase.Output {
        TODO("Not yet implemented")
    }
}