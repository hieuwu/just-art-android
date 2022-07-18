package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWork
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justartsdk.artworks.v1.dto.ArtWorksListDto
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorksViewModel @Inject constructor(private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase) :
    ViewModel() {

    private val _artWorksList = MutableLiveData<List<ArtWork>>()
    val artWorksList: LiveData<List<ArtWork>> = _artWorksList


    private var _artworksNetwork = MutableLiveData<ArtWorksListDto?>()
    val artworksNetwork: LiveData<ArtWorksListDto?> = _artworksNetwork

    private val _navigateToSelectedProperty = MutableLiveData<ArtWork?>()
    val navigateToSelectedProperty: LiveData<ArtWork?>
        get() = _navigateToSelectedProperty
    init {
        _artWorksList.value = listOf(
            ArtWork(
                "1", "Starry Night and the Astronauts",
                "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg"
            ),
            ArtWork(
                "2", "The Bedroom",
                "https://www.artic.edu/iiif/2/25c31d8d-21a4-9ea1-1d73-6a2eca4dda7e/full/843,/0/default.jpg"
            ),
            ArtWork(
                "3", "Buddha Shakyamuni Seated in Meditation (Dhyanamudra)",
                "https://www.artic.edu/iiif/2/0675f9a9-1a7b-c90a-3bb6-7f7be2afb678/full/843,/0/default.jpg"
            ),
            ArtWork(
                "4", "Many Mansions",
                "https://www.artic.edu/iiif/2/d94d0e3d-5d89-ce07-ee0f-7fa6d8def8ab/full/843,/0/default.jpg"
            ),
            ArtWork(
                "5", "A Sunday on La Grande Jatte — 1884",
                "https://www.artic.edu/iiif/2/2d484387-2509-5e8e-2c43-22f9981972eb/full/843,/0/default.jpg"
            ),
            ArtWork(
                "6", "Untitled",
                "https://www.artic.edu/iiif/2/7690dd6e-05ed-773c-a80e-e7cc4eb879cc/full/843,/0/default.jpg"
            ),
            ArtWork(
                "7", "Weaving",
                "https://www.artic.edu/iiif/2/96e26f4f-c578-b14c-2714-2a565f19e0d0/full/843,/0/default.jpg"
            ),
            ArtWork(
                "8", "Paris Street; Rainy Day",
                "https://www.artic.edu/iiif/2/f8fd76e9-c396-5678-36ed-6a348c904d27/full/843,/0/default.jpg"
            ),
            ArtWork(
                "9", "Coronation Stone of Motecuhzoma II (Stone of the Five Suns)",
                "https://www.artic.edu/iiif/2/ea8c5d62-6ce8-88e8-feb1-e0053cf534c5/full/843,/0/default.jpg"
            ),
            ArtWork(
                "10", "Nightlife",
                "https://www.artic.edu/iiif/2/ec19d5f1-ae0f-5186-d421-4a53dca5fb90/full/843,/0/default.jpg"
            )
        )
    }

    fun retrieveData() {
        viewModelScope.launch {
            when (val result = retrieveArtWorksUseCase.execute(RetrieveArtWorksUseCase.Input())) {
                is RetrieveArtWorksUseCase.Result.Success -> {
                    result.data?.let {
                        _artworksNetwork.value = it.response
                    }
                }
            }
        }
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun displayPropertyDetails(artWorkProperty: ArtWork) {
        _navigateToSelectedProperty.value = artWorkProperty
    }
}