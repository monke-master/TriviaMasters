package com.monke.triviamasters.data

import com.monke.triviamasters.domain.mockedCategories
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(

): CategoryRepository {

    private val categoriesList = mockedCategories

    override fun getCategories(): List<Category> {
        return categoriesList
    }


}