package com.monke.triviamasters.ui.mainScreenFeature.modesFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.useCases.game.SaveGameModeUseCase
import javax.inject.Inject

class ModesViewModel(
    private val saveGameModeUseCase: SaveGameModeUseCase
): ViewModel() {

    fun saveGameMode(gameMode: GameMode) {
        saveGameModeUseCase.execute(gameMode)
    }

    class Factory @Inject constructor(
        private val saveGameModeUseCase: SaveGameModeUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ModesViewModel(
                saveGameModeUseCase = saveGameModeUseCase
            ) as T
        }
    }


}