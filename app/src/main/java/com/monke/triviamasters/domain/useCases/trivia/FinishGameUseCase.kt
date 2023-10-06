package com.monke.triviamasters.domain.useCases.trivia

import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

class FinishGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun execute() {
        gameRepository.createGame(null)
    }
}