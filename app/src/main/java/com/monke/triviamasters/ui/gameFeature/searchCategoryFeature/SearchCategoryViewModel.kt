package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.useCases.category.GetCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SearchCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SearchCategoryViewModel (
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchCategoriesUseCase: SearchCategoriesUseCase
): ViewModel() {

    private val _categories = MutableStateFlow(getCategoriesUseCase.execute())
    val categories = _categories.asStateFlow()

    private val _selectedCategories = ArrayList<Category>()

    fun searchCategory(categoryTitle: String) {
        _categories.value = searchCategoriesUseCase.execute(categoryTitle)
    }


    class Factory @Inject constructor(
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val searchCategoriesUseCase: SearchCategoriesUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchCategoryViewModel(
                getCategoriesUseCase = getCategoriesUseCase,
                searchCategoriesUseCase = searchCategoriesUseCase
            ) as T
        }
    }

}