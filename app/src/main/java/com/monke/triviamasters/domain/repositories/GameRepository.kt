package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings

interface GameRepository {

    fun getGameSettings(): GameSettings

    fun setGameMode(gameMode: GameMode)
}