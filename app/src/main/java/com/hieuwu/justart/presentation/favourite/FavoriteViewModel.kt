package com.hieuwu.justart.presentation.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.GetFavoriteArtWorkUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteArtWorkUseCase: GetFavoriteArtWorkUseCase
) : ViewModel() {

    private val _favoriteArtWork = MutableLiveData<List<ArtWorkDo>?>()
    val favoriteArtWork: LiveData<List<ArtWorkDo>?> = _favoriteArtWork

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showListEmptyError = MutableLiveData(false)
    val showListEmptyError: LiveData<Boolean> = _showListEmptyError

    init {
        getFavoriteArtWork(
            onBeforeExecute = { onBeforeExecute() },
            onAfterExecute = { onAfterExecute() })
    }

    fun updateFavoriteArtWork() {
        getFavoriteArtWork(
            onBeforeExecute = { onBeforeExecute() },
            onAfterExecute = { onAfterExecute() })
    }

    private fun onBeforeExecute() {
        _isLoading.value = true
    }

    private fun onAfterExecute() {
        _isLoading.value = false
    }

    private fun getFavoriteArtWork(
        onBeforeExecute: () -> Unit,
        onAfterExecute: () -> Unit
    ) {
        viewModelScope.launch {
            onBeforeExecute()
            when (val result =
                getFavoriteArtWorkUseCase.execute(GetFavoriteArtWorkUseCase.Input())) {
                is GetFavoriteArtWorkUseCase.Result.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _showListEmptyError.value = true
                    } else {
                        _favoriteArtWork.value = result.data
                    }
                }
            }
        }.invokeOnCompletion {
            onAfterExecute()
        }
    }
}