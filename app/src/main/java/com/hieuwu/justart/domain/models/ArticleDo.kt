package com.hieuwu.justart.domain.models

data class ArticleDo (
    val id: Int,
    val title: String,
    val imageUrl: String? = null,
    val artistDisplay: String? = null,
    val copy: String? = null
)