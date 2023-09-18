package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.useCases.category.GetCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SearchCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SelectCategoryUseCase
import com.monke.triviamasters.ui.uiModels.CategoryUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchCategoryViewModel (
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase,
    private val selectCategoryUseCase: SelectCategoryUseCase
): ViewModel() {

    private val _categories = MutableStateFlow(
        getCategoriesUseCase.execute().map { CategoryUi(it, false) })
    val categories = _categories.asStateFlow()

    private val selectedCategories = HashMap<Int, Category>()

    fun searchCategory(categoryTitle: String) {
        _categories.value = searchCategoriesUseCase
            .execute(categoryTitle)
            .map { CategoryUi(it, selectedCategories[it.id] != null) }
    }


    fun setCategorySelected(categoryUi: CategoryUi, isSelected: Boolean) {
        if (isSelected)
            selectedCategories[categoryUi.category.id] = categoryUi.category
        else
            selectedCategories.remove(categoryUi.category.id)
        _categories.value = selectCategoryUseCase.execute(
            categoriesList = _categories.value,
            isSelected = isSelected,
            categoryUi = categoryUi
        )
    }

    fun selectedCategories(): Collection<Category> = selectedCategories.values

    class Factory @Inject constructor(
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val searchCategoriesUseCase: SearchCategoriesUseCase,
        private val selectCategoryUseCase: SelectCategoryUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchCategoryViewModel(
                getCategoriesUseCase = getCategoriesUseCase,
                searchCategoriesUseCase = searchCategoriesUseCase,
                selectCategoryUseCase = selectCategoryUseCase
            ) as T
        }
    }

}