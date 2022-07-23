package com.hieuwu.justart.presentation.artworkdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase

class ArtWorkDetailsViewModelFactory(private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtWorkDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtWorkDetailsViewModel(retrieveArtWorkDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}