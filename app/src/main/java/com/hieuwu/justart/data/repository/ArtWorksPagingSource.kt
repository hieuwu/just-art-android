package com.hieuwu.justart.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.mapper.asDomainModel
import com.hieuwu.justartsdk.ApiResult
import com.hieuwu.justartsdk.artworks.v1.ArtWorksService
import com.hieuwu.justartsdk.artworks.v1.domain.ArtWorksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val STARTING_KEY = 0

class ArtWorksPagingSource @Inject constructor(private val artWorksService: ArtWorksService) :
    PagingSource<Int, ArtWorkDo>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtWorkDo> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)
        val res: ApiResult<ArtWorksResponse>
        withContext(Dispatchers.IO) {
            res = artWorksService.getArtWorks(limit = 12, page = 12)
        }
        val data = res.response?.artWorks?.asDomainModel()!!
        return LoadResult.Page(
            data = data,
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ArtWorkDo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
//        val index = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(anchorPosition - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = Integer.max(STARTING_KEY, key)
}