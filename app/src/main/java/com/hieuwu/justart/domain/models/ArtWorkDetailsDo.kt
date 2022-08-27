package com.hieuwu.justart.domain.models

data class ArtWorkDetailsDo(
    var id: Int? = null,
    var title: String? = null,
    var mainReferenceNumber: String? = null,
    var dateDisplay: String? = null,
    var artistDisplay: String? = null,
    var placeOfOrigin: String? = null,
    var dimensions: String? = null,
    var mediumDisplay: String? = null,
    var creditLine: String? = null,
    var publicationHistory: String? = null,
    var exhibitionHistory: String? = null,
    var provenanceText: String? = null,
    var artistTitle: String? = null,
    val imageUrl: String? = null
)