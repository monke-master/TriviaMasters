package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Result

interface GameRepository {

    fun getGameSettings(): GameSettings

    fun setGameMode(gameMode: GameMode)

    fun saveGameSettings(gameSettings: GameSettings)

    fun createGame(game: Game)
}