package com.hieuwu.justart.presentation.artworks

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.GetFavoriteUseCase
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_ITEMS_PER_PAGE = 32

class ArtWorksViewModel @Inject constructor(
    private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val artworkRepository: ArtworkRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _showError = MutableLiveData(false)
    val showError: LiveData<Boolean> = _showError

    private fun onBeforeExecute() {
        _isLoading.value = true
    }

    private fun onAfterExecute() {
        _isLoading.value = false
    }

    val artWorkList: Flow<PagingData<ArtWorkDo>> = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_ITEMS_PER_PAGE,
            enablePlaceholders = false,
            initialLoadSize = DEFAULT_ITEMS_PER_PAGE
        ),
        pagingSourceFactory = { artworkRepository.artWorkPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)


    private suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean =
        getFavoriteUseCase.isArtworkFavorite(artwork)

    private fun deleteArtworkFavorite(artwork: ArtWorkDo) {
        viewModelScope.launch {
            getFavoriteUseCase.deleteFavoriteArtwork(artwork)
        }
    }

    fun updateFavoriteStatus(artWork: ArtWorkDo) {
        artWork.isFavorite = !artWork.isFavorite
        viewModelScope.launch {
            if (isArtworkFavorite(artWork)) {
                deleteArtworkFavorite(artWork)
                artWork.isFavorite = false
            } else {
                saveArtworkFavorite(artWork)
                artWork.isFavorite = true
            }
        }
    }

    private suspend fun saveArtworkFavorite(artwork: ArtWorkDo) {
        viewModelScope.launch {
            getFavoriteUseCase.saveFavoriteArtwork(artwork)
        }
    }
}