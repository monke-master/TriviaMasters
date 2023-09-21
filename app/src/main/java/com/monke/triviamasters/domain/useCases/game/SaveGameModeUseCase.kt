package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveGameModeUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(gameMode: GameMode) {
        gameRepository.saveGameSettings(gameRepository.getGameSettings()
            .first().copy(gameMode = gameMode))
    }
}