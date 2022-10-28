package com.hieuwu.justart.domain.models

data class ExhibitionsDo(
    val id: Int?,
    val title: String?,
    val isFeatured: Boolean?,
    val shortDescription: String?,
    val webUrl: String?,
    val imageUrl: String?,
    val status: String?,
    val aicStartAt: String?,
    val aicEndAt: String?,
    val galleryTitle: String?,
    val imageId: String?,
    val altImageIds: List<String>,
)
