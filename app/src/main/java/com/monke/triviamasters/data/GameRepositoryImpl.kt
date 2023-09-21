package com.monke.triviamasters.data

import android.util.Log
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AppScope
class GameRepositoryImpl @Inject constructor(

): GameRepository
{

    private var gameSettings = MutableStateFlow(GameSettings(GameMode.OwnGame))
    private var game: Game? = null

    override fun getGameSettings(): Flow<GameSettings> {
        return gameSettings
    }


    override fun saveGameSettings(gameSettings: GameSettings) {
        this.gameSettings.value = gameSettings
    }

    override fun getSelectedCategories(): List<Category>? = gameSettings.value.selectedCategories

    override fun createGame(game: Game) {
        this.game = game
        Log.d("GameRepository", "questions: ${game.questionsList.joinToString(separator = " ")}" )
    }
}