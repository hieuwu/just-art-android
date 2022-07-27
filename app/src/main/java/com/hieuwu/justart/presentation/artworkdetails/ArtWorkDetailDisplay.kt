package com.hieuwu.justart.presentation.artworkdetails

import com.hieuwu.justart.domain.models.ArtWorkDetailsDo

open class ArtWorkDetailDisplay {
    class Thumbnail(val imageUrl: String) : ArtWorkDetailDisplay()
    class CollapseSection(val title: String?, val content: String?) : ArtWorkDetailDisplay()
    class Text(val text: String) : ArtWorkDetailDisplay()
    class Section(val title: String, val content: String) : ArtWorkDetailDisplay()
}


fun mapToDisplay(artWorkDetailsDo: ArtWorkDetailsDo?): List<ArtWorkDetailDisplay> {
    val displayList = mutableListOf<ArtWorkDetailDisplay>()
    artWorkDetailsDo?.imageUrl?.let { ArtWorkDetailDisplay.Thumbnail(imageUrl = it) }
    artWorkDetailsDo?.title?.let { ArtWorkDetailDisplay.Text(text = it) }
    artWorkDetailsDo?.dateDisplay?.let { ArtWorkDetailDisplay.Text(text = it) }
    artWorkDetailsDo?.artistDisplay?.let { ArtWorkDetailDisplay.Text(text = it) }
    artWorkDetailsDo?.artistTitle?.let {
        ArtWorkDetailDisplay.Section(
            title = "Artist",
            content = it,
        )
    }
    artWorkDetailsDo?.title?.let {
        ArtWorkDetailDisplay.Section(
            title = "Title",
            content = it,
        )
    }

    artWorkDetailsDo?.placeOfOrigin?.let {
        ArtWorkDetailDisplay.Section(
            title = "Origin",
            content = it,
        )
    }

    artWorkDetailsDo?.dateDisplay?.let {
        ArtWorkDetailDisplay.Section(
            title = "Date",
            content = it,
        )
    }

    artWorkDetailsDo?.mediumDisplay?.let {
        ArtWorkDetailDisplay.Section(
            title = "Medium",
            content = it,
        )
    }

    artWorkDetailsDo?.dimensions?.let {
        ArtWorkDetailDisplay.Section(
            title = "Dimensions",
            content = it,
        )
    }

    artWorkDetailsDo?.creditLine?.let {
        ArtWorkDetailDisplay.Section(
            title = "Credit Line",
            content = it,
        )
    }

    artWorkDetailsDo?.mainReferenceNumber?.let {
        ArtWorkDetailDisplay.Section(
            title = "Reference Number",
            content = it,
        )
    }

    artWorkDetailsDo?.publicationHistory?.let {
        ArtWorkDetailDisplay.CollapseSection(
            title = "Publication history",
            content = it,
        )
    }

    artWorkDetailsDo?.exhibitionHistory?.let {
        ArtWorkDetailDisplay.CollapseSection(
            title = "Exhibition history",
            content = it,
        )
    }

    artWorkDetailsDo?.provenanceText?.let {
        ArtWorkDetailDisplay.CollapseSection(
            title = "Provenance",
            content = it,
        )
    }

    return displayList
}