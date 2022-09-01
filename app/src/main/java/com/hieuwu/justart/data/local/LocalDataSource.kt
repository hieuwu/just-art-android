package com.hieuwu.justart.data.local

import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.mapper.asArtworkEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LocalDataSource @Inject constructor(private val artworkDao: ArtworkDao) {
    suspend fun saveFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.saveFavoriteArtwork(artwork.asArtworkEntity())
    }

    suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.deleteFavoriteArtwork(artwork.asArtworkEntity())
    }

    suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean {
        return artworkDao.getArtworkById(artwork.id) != null
    }
}