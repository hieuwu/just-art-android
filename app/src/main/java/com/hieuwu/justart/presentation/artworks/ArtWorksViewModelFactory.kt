package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase

class ArtWorksViewModelFactory(private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtWorksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtWorksViewModel(retrieveArtWorksUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}