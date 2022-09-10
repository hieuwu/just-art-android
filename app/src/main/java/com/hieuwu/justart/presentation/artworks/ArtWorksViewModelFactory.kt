package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.usecases.GetFavoriteUseCase
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase

class ArtWorksViewModelFactory(
    private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val artworkRepository: ArtworkRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtWorksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtWorksViewModel(retrieveArtWorksUseCase, getFavoriteUseCase, artworkRepository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}