package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.ui.uiModels.CategoryUi
import javax.inject.Inject

class SelectCategoryUseCase @Inject constructor() {

    fun execute(
        categoriesList: List<CategoryUi>,
        categoryUi: CategoryUi,
        isSelected: Boolean
    ): List<CategoryUi> {
        val copiedList = categoriesList.toMutableList()
        copiedList[categoriesList.indexOf(categoryUi)] = categoryUi.copy(isSelected = isSelected)
        return copiedList
    }
}