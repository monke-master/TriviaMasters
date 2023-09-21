package com.monke.triviamasters.ui.gameFeature.descriptionFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.useCases.game.GetGameSettingsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModeDescriptionViewModel (
    private val getGameSettingsUseCase: GetGameSettingsUseCase
): ViewModel() {

    lateinit var gameMode: GameMode
        private set

    init {
        viewModelScope.launch {
            gameMode = getGameSettingsUseCase.execute().first().gameMode
        }
    }

    class Factory @Inject constructor(
        private val getGameSettingsUseCase: GetGameSettingsUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ModeDescriptionViewModel(
                getGameSettingsUseCase = getGameSettingsUseCase
            ) as T
        }

    }
}