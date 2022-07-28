package com.hieuwu.justart.presentation.artworkdetails

import com.hieuwu.justart.domain.models.ArtWorkDetailsDo

open class ArtWorkDetailDisplay {
    class Thumbnail(val imageUrl: String) : ArtWorkDetailDisplay()
    class CollapseSection(val title: String?, val content: String?) : ArtWorkDetailDisplay()
    class Title(val text: String) : ArtWorkDetailDisplay()

    class Text(val text: String) : ArtWorkDetailDisplay()
    class Section(val title: String, val content: String) : ArtWorkDetailDisplay()
}


fun mapToDisplay(artWorkDetailsDo: ArtWorkDetailsDo?): List<ArtWorkDetailDisplay> {
    val displayList = mutableListOf<ArtWorkDetailDisplay>()
    artWorkDetailsDo?.imageUrl?.let { displayList.add(ArtWorkDetailDisplay.Thumbnail(imageUrl = it)) }
    artWorkDetailsDo?.title?.let { displayList.add(ArtWorkDetailDisplay.Title(text = it)) }
    artWorkDetailsDo?.dateDisplay?.let { displayList.add(ArtWorkDetailDisplay.Text(text = it)) }
    artWorkDetailsDo?.artistDisplay?.let { displayList.add(ArtWorkDetailDisplay.Text(text = it)) }
    artWorkDetailsDo?.artistTitle?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Artist",
                content = it,
            )
        )
    }
    artWorkDetailsDo?.title?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Title",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.placeOfOrigin?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Origin",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.dateDisplay?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Date",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.mediumDisplay?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Medium",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.dimensions?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Dimensions",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.creditLine?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Credit Line",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.mainReferenceNumber?.let {
        displayList.add(
            ArtWorkDetailDisplay.Section(
                title = "Reference Number",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.publicationHistory?.let {
        displayList.add(
            ArtWorkDetailDisplay.CollapseSection(
                title = "Publication history",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.exhibitionHistory?.let {
        displayList.add(
            ArtWorkDetailDisplay.CollapseSection(
                title = "Exhibition history",
                content = it,
            )
        )
    }

    artWorkDetailsDo?.provenanceText?.let {
        displayList.add(
            ArtWorkDetailDisplay.CollapseSection(
                title = "Provenance",
                content = it,
            )
        )
    }

    return displayList
}