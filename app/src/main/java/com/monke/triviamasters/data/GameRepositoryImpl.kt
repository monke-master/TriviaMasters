package com.monke.triviamasters.data

import android.util.Log
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@AppScope
class GameRepositoryImpl @Inject constructor(

): GameRepository
{

    private var gameSettings = MutableStateFlow(GameSettings(GameMode.OwnGame))
    private var game: MutableStateFlow<Game?> = MutableStateFlow(null)
    private var playedGames = ArrayList<Game>()

    override fun getGameSettings(): Flow<GameSettings> {
        return gameSettings
    }


    override fun saveGameSettings(gameSettings: GameSettings) {
        this.gameSettings.value = gameSettings
    }

    override fun getSelectedCategories(): List<Category>? = gameSettings.value.selectedCategories

    override fun createGame(game: Game) {
        this.game.value = game
        Log.d("GameRepository", "questions: ${game.questionsList.joinToString(separator = " ")}" )
    }

    override fun getGame(): StateFlow<Game?> = game

    override suspend fun addPlayedGame(game: Game): kotlin.Result<Any?> {
        playedGames.add(game)
        return Result.success(null)
    }

    override fun getPlayedGames() = playedGames


}