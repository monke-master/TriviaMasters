package com.monke.triviamasters.domain.repositories

import androidx.paging.PagingData
import com.monke.triviamasters.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<PagingData<Category>>
}
