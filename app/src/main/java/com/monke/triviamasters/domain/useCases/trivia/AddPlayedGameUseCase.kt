package com.monke.triviamasters.domain.useCases.trivia

import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddPlayedGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(game: Game) = withContext(Dispatchers.IO) {
        gameRepository.addPlayedGame(game)
    }
}