package com.hieuwu.justart.presentation.artworkdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.CheckFavoriteArtWorkExistedUseCase
import com.hieuwu.justart.domain.usecases.DeleteFavoriteArtWorkUseCase
import com.hieuwu.justart.domain.usecases.RetrieveArtWorkDetailsUseCase
import com.hieuwu.justart.domain.usecases.SaveFavoriteArtWorkUseCase
import com.hieuwu.justart.mapper.asDo
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtWorkDetailsViewModel @Inject constructor(
    private val artWorkId: Int,
    private val retrieveArtWorkDetailsUseCase: RetrieveArtWorkDetailsUseCase,
    private val checkFavoriteArtWorkExistedUseCase: CheckFavoriteArtWorkExistedUseCase,
    private val deleteFavoriteArtWorkUseCase: DeleteFavoriteArtWorkUseCase,
    private val saveFavoriteArtWorkUseCase: SaveFavoriteArtWorkUseCase
) : ViewModel() {

    private val _displayList: MutableLiveData<List<ArtWorkDetailDisplay>?> = MutableLiveData()
    val displayList: LiveData<List<ArtWorkDetailDisplay>?> = _displayList

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title

    private val _showErrorView: MutableLiveData<Boolean> = MutableLiveData()
    val showErrorView: LiveData<Boolean> = _showErrorView

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var artistDisplay: String
    private lateinit var imageUrl: String

    init {
        getArtWorkDetails({ onBeforeExecute() }, { onAfterExecute() })
    }

    private fun onBeforeExecute() {
        _isLoading.value = true
    }

    private fun onAfterExecute() {
        _isLoading.value = false
    }

    fun onRefresh() {
        getArtWorkDetails({ onBeforeExecute() }, { onAfterExecute() })
    }

    private fun getArtWorkDetails(onBeforeExecute: () -> Unit, onAfterExecute: () -> Unit) {
        viewModelScope.launch {
            onBeforeExecute()
            when (val res = retrieveArtWorkDetailsUseCase.execute(RetrieveArtWorkDetailsUseCase.Input(artWorkId))) {
                is RetrieveArtWorkDetailsUseCase.Result.Success -> {
                    if (res.data == null) {
                        _showErrorView.value = true
                    } else {
                        _title.value = res.data.title ?: ""
                        _displayList.value = mapToDisplay(res.data)
                        _showErrorView.value = false
                        artistDisplay = res.data.artistDisplay ?: ""
                        imageUrl = res.data.imageUrl ?: ""
                    }
                }
                else -> {
                    _showErrorView.value = true
                }
            }

        }.invokeOnCompletion {
            onAfterExecute()
        }
    }

    suspend fun isArtWorkFavorite(artworkId: Int): ArtWorkDo? {
        when (val res = checkFavoriteArtWorkExistedUseCase.execute(CheckFavoriteArtWorkExistedUseCase.Input(artworkId))) {
            is CheckFavoriteArtWorkExistedUseCase.Result.Success -> {
                res.result?.let {
                    return it.asDo()
                }
                return null
            }
            else -> return null
        }
    }

    suspend fun saveOrDeleteFavoriteArtWork(artworkId: Int) {
        val res = isArtWorkFavorite(artworkId)
        res?.let {
            deleteFavoriteArtWorkUseCase.execute(DeleteFavoriteArtWorkUseCase.Input(it))
            return
        }
        saveFavoriteArtWorkUseCase.execute(SaveFavoriteArtWorkUseCase.Input(
            ArtWorkDo(
                artworkId,
                title = title.value!!,
                artistDisplay = artistDisplay,
                imageUrl = imageUrl,
                true
            )
        ))
    }
}