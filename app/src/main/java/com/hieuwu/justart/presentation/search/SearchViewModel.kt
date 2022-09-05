package com.hieuwu.justart.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.SearchArtWorkUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchArtWorkUseCase: SearchArtWorkUseCase
) : ViewModel() {

    private val _artWorksList = MutableLiveData<List<ArtWorkDo>>()
    val artWorksList: LiveData<List<ArtWorkDo>> = _artWorksList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchArtWorks()
    }

    fun searchArtWorks(query: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            when (val res =
                searchArtWorkUseCase.execute(SearchArtWorkUseCase.Input(query = query))) {
                is SearchArtWorkUseCase.Result.Success -> {
                    if (res.data != null) {
                        _artWorksList.value = res.data
                        _isLoading.value = false
                    }
                }
                is SearchArtWorkUseCase.Result.Failure -> {

                }
            }
        }
    }
}