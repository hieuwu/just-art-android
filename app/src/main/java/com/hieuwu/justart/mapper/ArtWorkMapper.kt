package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork

fun List<ArtWork>.asDomainModel(): List<ArtWorkDo> {
    return map {
        ArtWorkDo(
            id = it.id,
            title = it.title,
//            thumbnail = it.thumbnail?.asDomainModel(),
            thumbnail = it.thumbnail.asDomainModel(),
            artistDisplay = it.artistDisplay,
            dimensions = it.dimensions,
            mediumDisplay = it.mediumDisplay,
            inscriptions = it.inscriptions,
            creditLine = it.creditLine,
            placeOfOrigin = it.placeOfOrigin,
            publicationHistory = it.publicationHistory,
            exhibitionHistory = it.exhibitionHistory,
            provenanceText = it.provenanceText,
            imageUrl = it.imageUrl
        )
    }
}

fun ArtWork.Thumbnail.asDomainModel() = ArtWorkDo.Thumbnail(
    imageUrlBase64 = this.imageUrlBase64,
    width = this.width,
    height = this.height,
    altText = this.altText
)