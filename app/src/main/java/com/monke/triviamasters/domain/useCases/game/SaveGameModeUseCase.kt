package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

class SaveGameModeUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun execute(gameMode: GameMode) {
        gameRepository.setGameMode(gameMode)
    }
}