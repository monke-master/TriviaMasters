package com.monke.triviamasters.data

import android.util.Log
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.GameRepository
import javax.inject.Inject

@AppScope
class GameRepositoryImpl @Inject constructor(

): GameRepository
{

    private var gameSettings: GameSettings = GameSettings(GameMode.OwnGame)
    private var game: Game? = null

    override fun getGameSettings(): GameSettings {
        return gameSettings
    }

    override fun setGameMode(gameMode: GameMode) {
        gameSettings = gameSettings.copy(gameMode = gameMode)
    }

    override fun saveGameSettings(gameSettings: GameSettings) {
        this.gameSettings = gameSettings
    }

    override fun createGame(game: Game) {
        this.game = game
        Log.d("GameRepository", "questions: ${game.questionsList.joinToString(separator = " ")}" )
    }
}