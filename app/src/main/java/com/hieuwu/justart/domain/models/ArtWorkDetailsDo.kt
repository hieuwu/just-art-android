package com.hieuwu.justart.domain.models

data class ArtWorkDetailsDo(
    var id: Int? = null,
    var apiModel: String? = null,
    var apiLink: String? = null,
    var isBoosted: Boolean? = null,
    var title: String? = null,
    var altTitles: String? = null,
    var thumbnail: Thumbnail? = Thumbnail(),
    var mainReferenceNumber: String? = null,
    var dateDisplay: String? = null,
    var artistDisplay: String? = null,
    var placeOfOrigin: String? = null,
    var dimensions: String? = null,
    var mediumDisplay: String? = null,
    var inscriptions: String? = null,
    var creditLine: String? = null,
    var publicationHistory: String? = null,
    var exhibitionHistory: String? = null,
    var provenanceText: String? = null,
    var color: Color? = Color(),
    var latlon: String? = null,
    var isOnView: Boolean? = null,
    var galleryTitle: String? = null,
    var artworkTypeId: Int? = null,
    var departmentTitle: String? = null,
    var artistTitle: String? = null,

    ) {
    data class Thumbnail(
        var lqip: String? = null,
        var width: Int? = null,
        var height: Int? = null,
        var altText: String? = null
    )

    data class Color(
        var h: Int? = null,
        var l: Int? = null,
        var s: Int? = null,
        var percentage: Double? = null,
        var population: Int? = null

    )
}
