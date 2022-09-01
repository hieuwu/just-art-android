package com.hieuwu.justart.data.repository.impl

import com.hieuwu.justart.data.local.ArtworkDao
import com.hieuwu.justart.data.local.LocalDataSource
import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.mapper.asArtworkEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val artworkDao: ArtworkDao
) : ArtworkRepository {
    override suspend fun saveFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.saveFavoriteArtwork(artwork.asArtworkEntity())
    }

    override suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.deleteFavoriteArtwork(artwork.asArtworkEntity())
    }

    override suspend fun isArtworkFavorite(artwork: ArtWorkDo): Boolean {
        return artworkDao.getArtworkById(artwork.id) != null
    }
}