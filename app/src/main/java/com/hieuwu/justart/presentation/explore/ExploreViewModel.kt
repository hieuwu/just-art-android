package com.hieuwu.justart.presentation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ExhibitionsDo
import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase
) : ViewModel() {

    private val _exhibitions: MutableLiveData<List<ExhibitionsDo>> = MutableLiveData()
    val exhibitions: LiveData<List<ExhibitionsDo>> = _exhibitions

    init {
        getExhibitions()
    }

    private fun getExhibitions() {
        viewModelScope.launch {
            when (val res =
                retrieveExhibitionsUseCase.execute(RetrieveExhibitionsUseCase.Input())) {
                is RetrieveExhibitionsUseCase.Result.Success -> {
                    if (res.data != null) {
                        _exhibitions.value = res.data!!
                    } else {
                        // Handle data null
                    }
                }
                is RetrieveExhibitionsUseCase.Result.Failure -> {
                    // Handle generic errors
                }
            }
        }
    }
}