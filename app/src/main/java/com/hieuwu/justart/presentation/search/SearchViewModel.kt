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

    private val _showGenericError = MutableLiveData(false)
    val showGenericError: LiveData<Boolean> = _showGenericError

    private val _showEmptyListError = MutableLiveData(false)
    val showEmptyListError: LiveData<Boolean> = _showEmptyListError

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
                    }
                }
                is SearchArtWorkUseCase.Result.Failure -> {
                    handleError(res.error?.type)
                }
            }
        }.invokeOnCompletion {
            _isLoading.value = false
        }
    }

    private fun handleError(errorType: SearchArtWorkUseCase.ErrorType?) {
        when (errorType) {
            SearchArtWorkUseCase.ErrorType.GENERIC -> {
                _showGenericError.value = true
            }
            SearchArtWorkUseCase.ErrorType.EMPTY -> {
                _showEmptyListError.value = true
            }
            else -> {
                _showGenericError.value = true
            }
        }
    }

}