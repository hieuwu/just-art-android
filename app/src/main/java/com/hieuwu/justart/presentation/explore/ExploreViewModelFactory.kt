package com.hieuwu.justart.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieuwu.justart.domain.usecases.*

class ExploreViewModelFactory(
    private val retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExploreViewModel(
                retrieveExhibitionsUseCase = retrieveExhibitionsUseCase,
                getArticlesUseCase = getArticlesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}