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
    private val artWorkId: Int,
    private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase
) : ViewModel() {

    private val _artWorkDetails: MutableLiveData<ArtWorkDetailsDo?> = MutableLiveData()
    val artWorksDetails: LiveData<ArtWorkDetailsDo?>
        get() = _artWorkDetails

    private val _displayList: MutableLiveData<List<ArtWorkDetailDisplay>?> = MutableLiveData()
    val displayList: LiveData<List<ArtWorkDetailDisplay>?>
        get() = _displayList

    init {
        getArtWorkDetails()
    }

    private fun getArtWorkDetails() {
        viewModelScope.launch {
            when (val res =
                retrieveArtWorkDetailsUseCase.execute(RetrieveArtWorkDetailsUseCase.Input(artWorkId))) {
                is RetrieveArtWorkDetailsUseCase.Result.Success -> {
                    _artWorkDetails.value = res.data
                }
            }
        }
        _displayList.value = mapToDisplay(_artWorkDetails.value)
    }
}