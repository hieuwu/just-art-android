package com.hieuwu.justart.domain.models

data class ArtWorkDo(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail = ArtWorkDo.Thumbnail(),
    val artistDisplay: String,
    val dimensions: String? = null,
    val mediumDisplay: String? = null,
    val inscriptions: String? = null,
    val creditLine: String? = null,
    var placeOfOrigin: String? = null,
    val publicationHistory: String? = null,
    val exhibitionHistory: String? = null,
    val provenanceText: String? = null,
    val imageUrl: String? = null,
) {
    data class Thumbnail(
        val imageUrlBase64: String? = null,
        val width: Int? = null,
        val height: Int? = null,
        val altText: String? = null,
    )
}