package com.hieuwu.justart.presentation.artworkdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.CheckFavoriteArtWorkExistedUseCase
import com.hieuwu.justart.domain.usecases.DeleteFavoriteArtWorkUseCase
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.domain.usecases.SaveFavoriteArtWorkUseCase

class ArtWorkDetailsViewModelFactory(private val id: Int,
    private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase,
    private val checkFavoriteArtWorkExistedUseCase: CheckFavoriteArtWorkExistedUseCase,
    private val deleteFavoriteArtWorkUseCase: DeleteFavoriteArtWorkUseCase,
    private val saveFavoriteArtWorkUseCase: SaveFavoriteArtWorkUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtWorkDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtWorkDetailsViewModel(
                id,
                retrieveArtWorkDetailsUseCase,
                checkFavoriteArtWorkExistedUseCase,
                deleteFavoriteArtWorkUseCase,
                saveFavoriteArtWorkUseCase
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}