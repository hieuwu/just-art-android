package com.hieuwu.justart.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hieuwu.justart.domain.models.ArtWorkDo
import com.hieuwu.justart.domain.usecases.RetrieveArtWorksUseCase
import javax.inject.Inject

private const val STARTING_KEY = 0

class ArtWorksPagingSource @Inject constructor(private val retrieveArtWorksUseCase: RetrieveArtWorksUseCase) :
    PagingSource<Int, ArtWorkDo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtWorkDo> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + 1)
        val res = retrieveArtWorksUseCase.execute(
            RetrieveArtWorksUseCase.Input(
                limit = params.loadSize,
                page = start
            )
        ) as RetrieveArtWorksUseCase.Result.Success

        return LoadResult.Page(
            data = res.data ?: listOf(),
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ArtWorkDo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return ensureValidKey(anchorPosition - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = Integer.max(STARTING_KEY, key)
}