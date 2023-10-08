package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    fun execute() = categoryRepository.getCategories()

}