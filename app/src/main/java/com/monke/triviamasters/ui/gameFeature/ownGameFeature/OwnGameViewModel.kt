package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.QUESTIONS_AMOUNT_DEFAULT
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.useCases.game.CreateGameUseCase
import com.monke.triviamasters.domain.useCases.game.SaveGameSettingsUseCase
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnGameViewModel(
    private val saveGameSettingsUseCase: SaveGameSettingsUseCase,
    private val createGameUseCase: CreateGameUseCase
) : ViewModel() {

    private val _selectedCategories = ArrayList<Category>()
    val selectedCategories: List<Category> = _selectedCategories

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    var maxPrice: Int? = null
    var minPrice: Int? = null
    var questionsAmount: Int = QUESTIONS_AMOUNT_DEFAULT

    fun removeCategory(category: Category) {
        _selectedCategories.remove(category)
    }

    fun addCategories(categories: List<Category>) {
        _selectedCategories.addAll(categories)
    }

    /**
     * Создание игры с заданными настройками
     * изменяет uiState
     */
    fun createGame() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val gameSettings = GameSettings(
                selectedCategories = if (_selectedCategories.isEmpty()) null else _selectedCategories,
                gameMode = GameMode.OwnGame,
                minPrice = minPrice,
                maxPrice = maxPrice,
                questionsAmount = questionsAmount
            )
            val response = createGameUseCase.execute(gameSettings)
            if (response is Result.Failure) {
                _uiState.value = UiState.Error(response.exception)
                return@launch
            }
            saveGameSettingsUseCase.execute(gameSettings)
            _uiState.value = UiState.Success()
        }
    }

    class Factory @Inject constructor(
        private val saveGameSettingsUseCase: SaveGameSettingsUseCase,
        private val createGameUseCase: CreateGameUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OwnGameViewModel(
                saveGameSettingsUseCase = saveGameSettingsUseCase,
                createGameUseCase = createGameUseCase
            ) as T
        }

    }
}