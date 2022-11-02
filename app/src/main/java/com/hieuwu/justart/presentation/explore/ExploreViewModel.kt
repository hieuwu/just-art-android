package com.hieuwu.justart.presentation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.EventDo
import com.hieuwu.justart.domain.models.ExhibitionsDo
import com.hieuwu.justart.domain.usecases.GetEventsUseCase
import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase,
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _exhibitions: MutableLiveData<List<ExhibitionsDo>> = MutableLiveData()
    val exhibitions: LiveData<List<ExhibitionsDo>> = _exhibitions

    private val _events: MutableLiveData<List<EventDo>> = MutableLiveData()
    val events: LiveData<List<EventDo>> = _events

    init {
        viewModelScope.launch {
            getData()
        }
    }

    private suspend fun getData() {
        mutableListOf(
            viewModelScope.async {
                getExhibitions()
            },
            viewModelScope.async {
                getEvents()
            },
        )
    }

    private suspend fun getExhibitions(): List<ExhibitionsDo>? {
        when (val res = retrieveExhibitionsUseCase.execute(RetrieveExhibitionsUseCase.Input())) {
            is RetrieveExhibitionsUseCase.Result.Success -> {
                if (res.data != null) {
                    _exhibitions.value = res.data!!
                    return res.data
                } else {
                    // Handle data null
                }
            }
            is RetrieveExhibitionsUseCase.Result.Failure -> {
                // Handle generic errors
            }
        }
        return null
    }


    private suspend fun getEvents(): List<EventDo>? {
        when (val res = getEventsUseCase.execute(GetEventsUseCase.Input())) {
            is GetEventsUseCase.Result.Success -> {
                if (res.data != null) {
                    _events.value = res.data!!
                    return res.data
                }
            }
            is GetEventsUseCase.Result.Failure -> {

            }
        }
        return null
    }
}