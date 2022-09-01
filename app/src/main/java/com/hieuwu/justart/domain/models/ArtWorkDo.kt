package com.hieuwu.justart.domain.models

data class ArtWorkDo(
    val id: Int,
    val title: String,
    val artistDisplay: String?,
    val imageUrl: String? = null,
    var isFavorite: Boolean = false
)