package com.monke.triviamasters.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.monke.triviamasters.data.remote.CATEGORIES_PAGE_SIZE
import com.monke.triviamasters.data.remote.CategoryPageSource
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.mockedCategories
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@GameFragmentScope
class CategoryRepositoryImpl @Inject constructor(
    private val categoryPageSource: CategoryPageSource,
): CategoryRepository {


    override fun getCategories(): Flow<PagingData<Category>> {
        return Pager(
            config = PagingConfig(
                pageSize = CATEGORIES_PAGE_SIZE,

            ),
            pagingSourceFactory = {
                categoryPageSource
            }
        ).flow
    }

}