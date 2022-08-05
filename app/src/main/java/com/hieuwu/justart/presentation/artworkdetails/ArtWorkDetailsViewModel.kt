package com.hieuwu.justart.presentation.artworkdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorkDetailsViewModel @Inject constructor(
    private val artWorkId: Int,
    private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase
) : ViewModel() {

    private val _displayList: MutableLiveData<List<ArtWorkDetailDisplay>?> = MutableLiveData()
    val displayList: LiveData<List<ArtWorkDetailDisplay>?> = _displayList
    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title


    init {
        getArtWorkDetails()
    }

    private fun getArtWorkDetails() {
        viewModelScope.launch {
            when (val res =
                retrieveArtWorkDetailsUseCase.execute(RetrieveArtWorkDetailsUseCase.Input(artWorkId))) {
                is RetrieveArtWorkDetailsUseCase.Result.Success -> {
                    _title.value = res.data?.title ?: ""
                    _displayList.value = mapToDisplay(res.data)
                }
            }
        }
    }
}