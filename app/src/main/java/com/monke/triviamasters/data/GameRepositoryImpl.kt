package com.monke.triviamasters.data

import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

@AppScope
class GameRepositoryImpl @Inject constructor(

): GameRepository
{

    private var gameSettings: GameSettings = GameSettings(GameMode.OwnGame)

    override fun getGameSettings(): GameSettings {
        return gameSettings
    }

    override fun setGameMode(gameMode: GameMode) {
        gameSettings = gameSettings.copy(gameMode = gameMode)
    }
}