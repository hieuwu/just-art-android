package com.hieuwu.justart.data.repository.impl

import com.hieuwu.justart.data.local.ArtworkDao
import com.hieuwu.justart.data.repository.ArtworkRepository
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.models.ArtWorkEntity
import com.hieuwu.justart.mapper.asEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtworkRepositoryImpl @Inject constructor(
    private val artworkDao: ArtworkDao,
) : ArtworkRepository {
    override suspend fun getAllFavoriteArtwork(): List<ArtWorkEntity> {
        return artworkDao.getAllFavoriteArtWorks()
    }

    override suspend fun saveFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.saveFavoriteArtwork(artwork.asEntity())
    }

    override suspend fun deleteFavoriteArtwork(artwork: ArtWorkDo) {
        artworkDao.deleteFavoriteArtwork(artwork.asEntity())
    }

    override suspend fun getArtworkFavorite(artworkId: Int): ArtWorkEntity? {
        return artworkDao.getArtworkById(artworkId)
    }
}