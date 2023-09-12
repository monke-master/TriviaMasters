package com.monke.triviamasters.ui.gameFeature.descriptionFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.useCases.game.GetGameSettingsUseCase
import javax.inject.Inject

class ModeDescriptionViewModel (
    private val getGameSettingsUseCase: GetGameSettingsUseCase
): ViewModel() {

    val gameSettings = getGameSettingsUseCase.execute()

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