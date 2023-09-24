package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.CategoryRepository
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAvailableCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val gameRepository: GameRepository
) {

    fun execute(): List<Category> {
        val categories = categoryRepository.getCategories().toMutableList()
        gameRepository.getSelectedCategories()?.let {
            categories.removeAll(it)
        }
        return categories.sortedBy { it.title }
    }

}