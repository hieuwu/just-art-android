package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork

fun List<ArtWork>.asDomainModel(): List<ArtWorkDo> {
    return map {
        ArtWorkDo(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            artistDisplay = it.artistDisplay
        )
    }
}