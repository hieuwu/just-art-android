package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ExhibitionsDo
import com.hieuwu.justartsdk.exhibitions.v1.domain.Exhibition

fun Exhibition.asDomainModel() = ExhibitionsDo(
    id = this.id,
    title = this.title,
    isFeatured = this.isFeatured,
    shortDescription = this.shortDescription,
    webUrl = this.webUrl,
    imageUrl = this.imageUrl,
    status = this.status,
    aicStartAt = this.aicStartAt,
    aicEndAt = this.aicEndAt,
    galleryTitle = this.galleryTitle,
    imageId = this.imageId,
    altImageIds = this.altImageIds,
)