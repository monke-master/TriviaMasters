package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import javax.inject.Inject

class SearchCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    fun execute(categoryTitle: String): List<Category> {
        val categories = categoryRepository.getCategories()
        return categories
            .filter { it.title.lowercase().contains(categoryTitle.lowercase()) }
            .sortedBy { it.title }
    }
}