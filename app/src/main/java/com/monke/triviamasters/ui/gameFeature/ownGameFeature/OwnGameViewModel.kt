package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.QUESTIONS_COUNT_DEFAULT
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.useCases.category.RemoveSelectedCategoryUseCase
import com.monke.triviamasters.domain.useCases.game.CreateGameWithSettingsUseCase
import com.monke.triviamasters.domain.useCases.game.GetGameSettingsUseCase
import com.monke.triviamasters.domain.useCases.game.SaveGameSettingsUseCase
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class OwnGameViewModel(
    private val saveGameSettingsUseCase: SaveGameSettingsUseCase,
    private val createGameWithSettingsUseCase: CreateGameWithSettingsUseCase,
    private val getGameSettingsUseCase: GetGameSettingsUseCase,
    private val removeSelectedCategoryUseCase: RemoveSelectedCategoryUseCase
) : ViewModel() {

    private val gameSettings = getGameSettingsUseCase.execute()

    private val _selectedCategories = MutableStateFlow<List<Category>?>(null)
    val selectedCategories = _selectedCategories.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    var maxPrice: Int? = null
    var minPrice: Int? = null
    var questionsAmount: Int = QUESTIONS_COUNT_DEFAULT

    init {
        viewModelScope.launch {
            gameSettings.collect {
                _selectedCategories.value = it.selectedCategories
            }
        }
    }

    fun removeCategory(category: Category) {
        viewModelScope.launch {
            removeSelectedCategoryUseCase.execute(category)
        }
    }


    /**
     * Создание игры с заданными настройками
     * изменяет uiState
     */
    fun createGame() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val gameSettings = GameSettings(
                selectedCategories = _selectedCategories.value,
                gameMode = GameMode.OwnGame,
                minPrice = minPrice,
                maxPrice = maxPrice,
                questionsCount = questionsAmount
            )
            val response = createGameWithSettingsUseCase.execute(gameSettings)
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
        private val createGameWithSettingsUseCase: CreateGameWithSettingsUseCase,
        private val getGameSettingsUseCase: GetGameSettingsUseCase,
        private val removeSelectedCategoryUseCase: RemoveSelectedCategoryUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OwnGameViewModel(
                saveGameSettingsUseCase = saveGameSettingsUseCase,
                createGameWithSettingsUseCase = createGameWithSettingsUseCase,
                getGameSettingsUseCase = getGameSettingsUseCase,
                removeSelectedCategoryUseCase = removeSelectedCategoryUseCase
            ) as T
        }

    }
}