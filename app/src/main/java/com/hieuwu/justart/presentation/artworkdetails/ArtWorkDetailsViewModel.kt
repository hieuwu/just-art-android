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

    private val _showErrorview: MutableLiveData<Boolean> = MutableLiveData()
    val showErrorView: LiveData<Boolean> = _showErrorview

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        getArtWorkDetails({ onBeforeExecute() }, { onAfterExecute() })
    }

    private fun onBeforeExecute() {
        _isLoading.value = true
    }

    private fun onAfterExecute() {
        _isLoading.value = false
    }

    private fun getArtWorkDetails(onBeforeExecute: () -> Unit, onAfterExecute: () -> Unit) {
        viewModelScope.launch {
            onBeforeExecute()
            when (val res = retrieveArtWorkDetailsUseCase.execute(RetrieveArtWorkDetailsUseCase.Input(artWorkId))) {
                is RetrieveArtWorkDetailsUseCase.Result.Success -> {
                    if (res.data == null) {
                        _showErrorview.value = false
                    } else {
                        _title.value = res.data.title ?: ""
                        _displayList.value = mapToDisplay(res.data)
                    }
                }
                else -> {
                    _showErrorview.value = false
                }
            }

        }.invokeOnCompletion {
            onAfterExecute()
        }
    }
}