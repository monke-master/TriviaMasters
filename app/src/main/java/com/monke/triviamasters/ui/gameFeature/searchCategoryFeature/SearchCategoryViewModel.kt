package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Category
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCategoryViewModel (
    searchCategoryUseCases: SearchCategoryUseCases
): ViewModel() {

    private val getCategoriesUseCase = searchCategoryUseCases.getCategoriesUseCase
    private val selectCategoryUseCase = searchCategoryUseCases.selectCategoryUseCase

    val categories = getCategoriesUseCase.execute()


    fun selectCategory(category: Category) {
        viewModelScope.launch { selectCategoryUseCase.execute(category) }
    }

    class Factory @Inject constructor(
        private val searchCategoryUseCases: SearchCategoryUseCases
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchCategoryViewModel(searchCategoryUseCases) as T
        }
    }

}