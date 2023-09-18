package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

class SaveGameSettingsUseCase @Inject constructor(
    private val gameRepository: GameRepository,
) {

    fun execute(gameSettings: GameSettings) {
        gameRepository.saveGameSettings(gameSettings)
    }
}