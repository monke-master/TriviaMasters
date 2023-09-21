package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.useCases.category.GetAvailableCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SaveSelectedCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SearchCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SelectCategoryUseCase
import com.monke.triviamasters.ui.uiModels.CategoryUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCategoryViewModel (
    private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase,
    private val selectCategoryUseCase: SelectCategoryUseCase,
    private val saveSelectedCategoriesUseCase: SaveSelectedCategoriesUseCase
): ViewModel() {

    private val _categories = MutableStateFlow(getAvailableCategoriesUseCase
        .execute().map { CategoryUi(it, false) })
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

    fun saveCategories() {
        viewModelScope.launch {
            saveSelectedCategoriesUseCase.execute(selectedCategories.values)
        }
    }

    class Factory @Inject constructor(
        private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase,
        private val searchCategoriesUseCase: SearchCategoriesUseCase,
        private val selectCategoryUseCase: SelectCategoryUseCase,
        private val saveSelectedCategoriesUseCase: SaveSelectedCategoriesUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchCategoryViewModel(
                getAvailableCategoriesUseCase = getAvailableCategoriesUseCase,
                searchCategoriesUseCase = searchCategoriesUseCase,
                selectCategoryUseCase = selectCategoryUseCase,
                saveSelectedCategoriesUseCase = saveSelectedCategoriesUseCase
            ) as T
        }
    }

}