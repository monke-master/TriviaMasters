package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Category

interface CategoryRepository {

    fun getCategories(): List<Category>
}
