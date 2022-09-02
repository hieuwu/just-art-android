package com.hieuwu.justart.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchArtWorkUseCase: SearchArtWorkUseCase
) : ViewModel() {

    init {
        searchArtWorks(query = "monet")
    }
    private fun searchArtWorks(query: String) {
        viewModelScope.launch {
            val res = searchArtWorkUseCase.execute(
                SearchArtWorkUseCase.Input(
                    query = query
                ))
            val a = 0
        }
    }
}