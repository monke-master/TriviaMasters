package com.monke.triviamasters.ui.gameFeature.triviaFeature.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.ui.gameFeature.triviaFeature.trivia.GetLastGame
import javax.inject.Inject

class TriviaResultViewModel(
    private val getLastGame: GetLastGame
) : ViewModel() {

    val game = getLastGame.execute()

    class Factory @Inject constructor(
        private val getLastGame: GetLastGame
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TriviaResultViewModel(
                getLastGame = getLastGame
            ) as T
        }
    }
}