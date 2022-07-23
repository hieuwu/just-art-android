package com.hieuwu.justart.presentation.artworkdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDetailsDo
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorkDetailsViewModel @Inject constructor(
    private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase
) : ViewModel() {

    private val _artWorkDetails: MutableLiveData<ArtWorkDetailsDo?>? = null
    val artWorksDetails: LiveData<ArtWorkDetailsDo?>?
        get() = _artWorkDetails

    init {
        getArtWorkDetails()
    }

    private fun getArtWorkDetails() {
        viewModelScope.launch {
            when (val res = retrieveArtWorkDetailsUseCase.execute(RetrieveArtWorkDetailsUseCase.Input(1))) {
                is RetrieveArtWorkDetailsUseCase.Result.Success -> {
                    _artWorkDetails?.value = res.data
                }
            }
        }
    }
}