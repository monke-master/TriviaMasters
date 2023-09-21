package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGameSettings(): Flow<GameSettings>

    fun saveGameSettings(gameSettings: GameSettings)

    fun getSelectedCategories(): List<Category>?

    fun createGame(game: Game)
}