package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.models.ArtWorkEntity
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork

fun List<ArtWork>.asDomainModel(): List<ArtWorkDo> {
    return map {
        ArtWorkDo(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            artistDisplay = it.artistDisplay,
            isFavorite = false
        )
    }
}

fun List<ArtWorkEntity>.entityToDomainModel(): List<ArtWorkDo> {
    return map {
        ArtWorkDo(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            artistDisplay = it.artistDisplay,
            isFavorite = false
        )
    }
}

fun ArtWorkDo.asEntity(): ArtWorkEntity =
    ArtWorkEntity(this.id, this.title, this.artistDisplay, this.imageUrl)