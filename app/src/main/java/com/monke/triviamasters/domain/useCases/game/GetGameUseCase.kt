package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

class GetGameUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun execute() = gameRepository.getGame()
}