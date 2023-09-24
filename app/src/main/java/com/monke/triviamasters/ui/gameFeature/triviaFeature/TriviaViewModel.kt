package com.monke.triviamasters.ui.gameFeature.triviaFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.useCases.game.GetGameUseCase
import javax.inject.Inject

class TriviaViewModel(
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {

    val game = getGameUseCase.execute()




    class Factory @Inject constructor(
        private val getGameUseCase: GetGameUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TriviaViewModel(
                getGameUseCase = getGameUseCase
            ) as T
        }
    }
}