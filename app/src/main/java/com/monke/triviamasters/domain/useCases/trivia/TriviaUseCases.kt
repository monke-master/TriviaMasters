package com.monke.triviamasters.domain.useCases.trivia

import com.monke.triviamasters.domain.useCases.game.GetGameUseCase
import com.monke.triviamasters.domain.useCases.player.SaveStatsUseCase
import javax.inject.Inject

class TriviaUseCases @Inject constructor(
    val getGameUseCase: GetGameUseCase,
    val updateCurrentGameUseCase: UpdateCurrentGameUseCase,
    val addPlayedGameUseCase: AddPlayedGameUseCase,
    val finishGameUseCase: FinishGameUseCase,
    val saveStatsUseCase: SaveStatsUseCase
) {
}