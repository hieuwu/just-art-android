package com.hieuwu.justart.domain.usecases.impl

import com.hieuwu.justart.domain.models.ArticleDo
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
        val data: List<ArticleDo>?
        withContext(Dispatchers.IO) {
            val res = articlesService.getArticles()
             data = res.response?.articles?.map { it.asDomainModel() }
        }
        return GetArticlesUseCase.Result.Success(data = data)
    }
}