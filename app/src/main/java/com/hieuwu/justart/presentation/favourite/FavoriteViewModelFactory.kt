package com.hieuwu.justart.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.GetFavoriteArtWorkUseCase

class FavoriteViewModelFactory(
    private val getFavoriteArtWorkUseCase: GetFavoriteArtWorkUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(
                getFavoriteArtWorkUseCase = getFavoriteArtWorkUseCase
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}