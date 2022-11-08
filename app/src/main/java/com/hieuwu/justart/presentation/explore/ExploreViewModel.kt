package com.hieuwu.justart.presentation.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArticleDo
import com.hieuwu.justart.domain.models.EventDo
import com.hieuwu.justart.domain.models.ExhibitionsDo
import com.hieuwu.justart.domain.usecases.GetArticlesUseCase
import com.hieuwu.justart.domain.usecases.GetEventsUseCase
import com.hieuwu.justart.domain.usecases.RetrieveExhibitionsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val retrieveExhibitionsUseCase: RetrieveExhibitionsUseCase,
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _exhibitions: MutableLiveData<List<ExhibitionsDo>> = MutableLiveData()
    val exhibitions: LiveData<List<ExhibitionsDo>> = _exhibitions

    private val _articles: MutableLiveData<List<ArticleDo>> = MutableLiveData()
    val articles: LiveData<List<ArticleDo>> = _articles

    init {
        getData()
    }

    private fun getData() {
        getEvents()
        viewModelScope.async {
            getExhibitions()
        }
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

    private fun getEvents() {
        viewModelScope.launch {
            when (val res = getArticlesUseCase.execute(GetArticlesUseCase.Input())) {
                is GetArticlesUseCase.Result.Success -> {
                    if (res.data != null) {
                        _articles.value = res.data!!
                    }
                }
                is GetArticlesUseCase.Result.Failure -> {

                }
            }
        }
    }
}