package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {

    fun getGameSettings(): Flow<GameSettings>

    fun saveGameSettings(gameSettings: GameSettings)

    fun getSelectedCategories(): List<Category>?

    fun createGame(game: Game?)

    fun getGame(): StateFlow<Game?>

    suspend fun addPlayedGame(game: Game): Result<Any?>

    fun getPlayedGames(): List<Game>
}