package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    fun execute(): List<Category> {
        return categoryRepository.getCategories().sortedBy { it.title }
    }
}