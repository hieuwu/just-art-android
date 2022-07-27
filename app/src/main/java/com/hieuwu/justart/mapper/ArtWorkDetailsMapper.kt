package com.hieuwu.justart.mapper

import com.hieuwu.justart.domain.models.ArtWorkDetailsDo
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorkDetails

fun ArtWorkDetails.asDomainModel() = ArtWorkDetailsDo(
    id = this.id,
    thumbnail = this.thumbnail?.asDomainModel(),
    dateDisplay = this.dateDisplay,
    artistDisplay = this.artistDisplay,
    publicationHistory = this.publicationHistory,
    isOnView = this.isOnView,
    galleryTitle = this.galleryTitle,
    artistTitle = this.artistTitle,
    title = this.title,
    departmentTitle = this.departmentTitle,
    placeOfOrigin = this.placeOfOrigin,
    mediumDisplay = this.mediumDisplay,
    dimensions = this.dimensions,
    creditLine = this.creditLine,
    mainReferenceNumber = this.mainReferenceNumber,
    exhibitionHistory = this.exhibitionHistory,
    provenanceText = this.provenanceText,
    imageUrl = this.imageUrl
)


fun ArtWorkDetails.Color.asDomainModel() = ArtWorkDetailsDo.Color(
    h = this.h,
    l = this.h,
    s = this.s,
    percentage = this.percentage,
    population = this.population

)

fun ArtWorkDetails.Thumbnail.asDomainModel() = ArtWorkDetailsDo.Thumbnail(
    lqip = this.lqip,
    width = this.width,
    height = this.height,
    altText = this.altText
)
