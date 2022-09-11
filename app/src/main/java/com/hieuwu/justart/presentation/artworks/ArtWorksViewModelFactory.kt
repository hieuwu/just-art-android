package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.*

class ArtWorksViewModelFactory(
    private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase,
    private val checkFavoriteArtWorkExistedUseCase: CheckFavoriteArtWorkExistedUseCase,
    private val deleteFavoriteArtWorkUseCase: DeleteFavoriteArtWorkUseCase,
    private val saveFavoriteArtWorkUseCase: SaveFavoriteArtWorkUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtWorksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtWorksViewModel(
                retrieveArtWorksUseCase = retrieveArtWorksUseCase,
                checkFavoriteArtWorkExistedUseCase = checkFavoriteArtWorkExistedUseCase,
                deleteFavoriteArtWorkUseCase = deleteFavoriteArtWorkUseCase,
                saveFavoriteArtWorkUseCase = saveFavoriteArtWorkUseCase
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}