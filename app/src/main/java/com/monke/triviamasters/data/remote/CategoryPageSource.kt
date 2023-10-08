package com.monke.triviamasters.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.monke.triviamasters.data.converters.toDomain
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.models.Category
import retrofit2.HttpException
import javax.inject.Inject

@GameFragmentScope
class CategoryPageSource @Inject constructor(
    private val triviaApi: TriviaApi
): PagingSource<Int, Category>() {

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = triviaApi.getCategories(offset = pageSize*page, count = pageSize)
            if (response.isSuccessful) {
                val categories = response.body()?.map { it.toDomain() } ?: ArrayList()
                val nextKey = if (categories.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                return LoadResult.Page(
                    data = categories,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            return LoadResult.Error(HttpException(response))
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}