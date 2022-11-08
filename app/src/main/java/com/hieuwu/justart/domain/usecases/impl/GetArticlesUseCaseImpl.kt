package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.usecases.GetArticlesUseCase
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.articles.v1.ArticlesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetArticlesUseCaseImpl @Inject constructor(
    private val articlesService: ArticlesService
) : GetArticlesUseCase {
    override suspend fun execute(input: GetArticlesUseCase.Input): GetArticlesUseCase.Result {
        withContext(Dispatchers.IO) {
            val res = articlesService.getArticles()
            val data = res.response?.articles?.map { it.asDomainModel() }
            return@withContext GetArticlesUseCase.Result.Success(data = data)
        }
        return GetArticlesUseCase.Result.Failure
    }
}