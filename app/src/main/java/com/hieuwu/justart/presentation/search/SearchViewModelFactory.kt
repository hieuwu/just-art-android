package com.hieuwu.justart.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase

class SearchViewModelFactory(private val searchArtWorkUseCase: SearchArtWorkUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchArtWorkUseCase) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}