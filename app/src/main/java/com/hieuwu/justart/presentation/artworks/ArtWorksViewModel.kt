package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorksViewModel @Inject constructor(private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase) :
    ViewModel() {

    private val _artWorksList = MutableLiveData<List<ArtWorkDo>>()
    val artWorksList: LiveData<List<ArtWorkDo>> = _artWorksList

    private val _navigateToSelectedProperty = MutableLiveData<ArtWorkDo?>()
    val navigateToSelectedProperty: LiveData<ArtWorkDo?>
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

    fun displayPropertyDetails(artWorkProperty: ArtWorkDo) {
        _navigateToSelectedProperty.value = artWorkProperty
    }
}