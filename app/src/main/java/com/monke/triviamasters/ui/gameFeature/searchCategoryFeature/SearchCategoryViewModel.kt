package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.MAX_QUESTIONS_COUNT
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class SearchCategoryViewModel (
    searchCategoryUseCases: SearchCategoryUseCases
): ViewModel() {

    private val getCategoriesUseCase = searchCategoryUseCases.getCategoriesUseCase
    private val selectCategoryUseCase = searchCategoryUseCases.selectCategoryUseCase
    private val createGameWithSettingsUseCase = searchCategoryUseCases.createGameWithSettingsUseCase

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()


    val categories = getCategoriesUseCase.execute()
    private lateinit var selectedCategory: Category


    fun selectCategory(category: Category) {
        viewModelScope.launch {
            selectedCategory = category
            selectCategoryUseCase.execute(category)
        }
    }

    fun createGame() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val gameSettings = GameSettings(
                selectedCategories = ArrayList<Category>().apply { add(selectedCategory) },
                gameMode = GameMode.Category,
                questionsCount = MAX_QUESTIONS_COUNT
            )
            val response = createGameWithSettingsUseCase.execute(gameSettings)
            if (response.isFailure) {
                response.exceptionOrNull()?.let {
                    _uiState.value = UiState.Error(it)
                }
                return@launch
            }
            _uiState.value = UiState.Success()
        }
    }

    class Factory @Inject constructor(
        private val searchCategoryUseCases: SearchCategoryUseCases
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchCategoryViewModel(searchCategoryUseCases) as T
        }
    }

}