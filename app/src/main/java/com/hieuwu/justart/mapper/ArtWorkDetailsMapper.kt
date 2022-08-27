package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArtWorkDetailsDo
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorkDetails

fun ArtWorkDetails.asDomainModel() = ArtWorkDetailsDo(
    id = this.id,
    dateDisplay = this.dateDisplay,
    artistDisplay = this.artistDisplay,
    publicationHistory = this.publicationHistory,
    title = this.title,
    placeOfOrigin = this.placeOfOrigin,
    mediumDisplay = this.mediumDisplay,
    dimensions = this.dimensions,
    creditLine = this.creditLine,
    mainReferenceNumber = this.mainReferenceNumber,
    exhibitionHistory = this.exhibitionHistory,
    provenanceText = this.provenanceText,
    imageUrl = this.imageUrl
)

