package com.monke.triviamasters.ui.gameFeature.extraHardFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.useCases.game.CreateExtraHardGameUseCase
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExtraHardViewModel(
    private val createExtraHardGameUseCase: CreateExtraHardGameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    fun createGame() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = createExtraHardGameUseCase.execute()
            when (result) {
                is Result.Success -> _uiState.value = UiState.Success()
                is Result.Failure -> _uiState.value = UiState.Error(result.exception)
            }
        }
    }


    class Factory @Inject constructor(
        private val createExtraHardGameUseCase: CreateExtraHardGameUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExtraHardViewModel(createExtraHardGameUseCase = createExtraHardGameUseCase) as T
        }
    }
}