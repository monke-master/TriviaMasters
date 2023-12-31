package com.monke.triviamasters.ui.gameFeature.fullyRandomFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.useCases.game.CreateRandomGameUseCase
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FullyRandomViewModel (
    private val createRandomGameUseCase: CreateRandomGameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    fun createGame() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = createRandomGameUseCase.execute()
            if (result.isSuccess)
                _uiState.value = UiState.Success()
//            else
//                result.exceptionOrNull()?.let { //_uiState.value = UiState.Error(it) }

        }
    }

    class Factory @Inject constructor(
        private val createRandomGameUseCase: CreateRandomGameUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FullyRandomViewModel(createRandomGameUseCase = createRandomGameUseCase) as T
        }

    }

}