package com.hieuwu.justart.presentation.artworks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWork
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import com.hieuwu.justartsdk.artworks.v1.dto.ArtWorksListDto
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorksViewModel @Inject constructor(private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase) :
    ViewModel() {

    private val _artWorksList = MutableLiveData<List<ArtWork>>()
    val artWorksList: LiveData<List<ArtWork>> = _artWorksList

    private val _navigateToSelectedProperty = MutableLiveData<ArtWork?>()
    val navigateToSelectedProperty: LiveData<ArtWork?>
        get() = _navigateToSelectedProperty

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch {
            when (val result = retrieveArtWorksUseCase.execute(RetrieveArtWorksUseCase.Input())) {
                is RetrieveArtWorksUseCase.Result.Success -> {
                    result.data?.let {
                        _artWorksList.value = result.data
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