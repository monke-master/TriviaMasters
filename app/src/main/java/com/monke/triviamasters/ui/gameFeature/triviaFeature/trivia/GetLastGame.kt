package com.monke.triviamasters.ui.gameFeature.triviaFeature.trivia

import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

class GetLastGame @Inject constructor(
    private val gameRepository: GameRepository
) {

    fun execute() = gameRepository.getPlayedGames().last()
}