package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase
) {

    fun execute(categoryTitle: String): List<Category> {
        val categories = getAvailableCategoriesUseCase.execute()
        return categories
            .filter { it.title.lowercase().contains(categoryTitle.lowercase()) }
            .sortedBy { it.title }
    }
}