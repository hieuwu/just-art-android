package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArticleDo
import com.hieuwu.justartsdk.articles.v1.domain.Article

fun Article.asDomainModel() = ArticleDo(
    id = this.id,
    title = this.title,
    artistDisplay = this.artistDisplay,
    imageUrl = this.imageUrl
)