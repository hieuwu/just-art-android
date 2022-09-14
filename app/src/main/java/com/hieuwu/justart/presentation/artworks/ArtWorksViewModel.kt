package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorksViewModel @Inject constructor(
    private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase,
    private val checkFavoriteArtWorkExistedUseCase: CheckFavoriteArtWorkExistedUseCase,
    private val deleteFavoriteArtWorkUseCase: DeleteFavoriteArtWorkUseCase,
    private val saveFavoriteArtWorkUseCase: SaveFavoriteArtWorkUseCase,
) : ViewModel() {

    private val _artWorksList = MutableLiveData<List<ArtWorkDo>>()
    val artWorksList: LiveData<List<ArtWorkDo>> = _artWorksList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _showError = MutableLiveData(false)
    val showError: LiveData<Boolean> = _showError

    init {
        retrieveData(onBeforeExecute = { onBeforeExecute() }, onAfterExecute = { onAfterExecute() })
    }

    fun onRefresh() {
        retrieveData(onBeforeExecute = { onBeforeExecute() }, onAfterExecute = { onAfterExecute() })
    }

    private fun onBeforeExecute() {
        _isLoading.value = true
    }

    private fun onAfterExecute() {
        _isLoading.value = false
    }

    private fun retrieveData(onBeforeExecute: () -> Unit, onAfterExecute: () -> Unit) {
        viewModelScope.launch {
            onBeforeExecute()
            when (val result = retrieveArtWorksUseCase.execute(RetrieveArtWorksUseCase.Input())) {
                is RetrieveArtWorksUseCase.Result.Success -> {
                    if (result.data == null) {
                        _showError.value = true
                    } else {
                        _artWorksList.value = result.data
                        _showError.value = false
                    }
                }
                else -> {
                    _showError.value = true
                }
            }
        }.invokeOnCompletion {
            onAfterExecute()
        }
    }

    private suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean {
        val res = checkFavoriteArtWorkExistedUseCase.execute(
            CheckFavoriteArtWorkExistedUseCase.Input(artwork.id)
        )
        if (res is CheckFavoriteArtWorkExistedUseCase.Result.Success) {
            return res.result
        }
        return false
    }

    private fun deleteArtworkFavorite(artwork: ArtWorkDo) {
        viewModelScope.launch {
            deleteFavoriteArtWorkUseCase.execute(DeleteFavoriteArtWorkUseCase.Input(artwork))
        }
    }

    fun updateFavoriteStatus(artWork: ArtWorkDo) {
        artWork.isFavorite = !artWork.isFavorite
        viewModelScope.launch {
            if (isArtworkFavorite(artWork)) {
                deleteArtworkFavorite(artWork)
                artWork.isFavorite = false
            } else {
                saveArtworkFavorite(artWork)
                artWork.isFavorite = true
            }
        }
    }

    private suspend fun saveArtworkFavorite(artwork: ArtWorkDo) {
        viewModelScope.launch {
            saveFavoriteArtWorkUseCase.execute(SaveFavoriteArtWorkUseCase.Input(artwork))
        }
    }
}