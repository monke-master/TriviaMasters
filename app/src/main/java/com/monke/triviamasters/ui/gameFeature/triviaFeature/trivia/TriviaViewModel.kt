package com.monke.triviamasters.ui.gameFeature.triviaFeature.trivia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.QUESTION_TIME_MILLIS
import com.monke.triviamasters.domain.useCases.trivia.AddPlayedGameUseCase
import com.monke.triviamasters.domain.useCases.game.GetGameUseCase
import com.monke.triviamasters.domain.useCases.trivia.FinishGameUseCase
import com.monke.triviamasters.domain.useCases.trivia.TriviaUseCases
import com.monke.triviamasters.domain.useCases.trivia.UpdateCurrentGameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TriviaViewModel(
    triviaUseCases: TriviaUseCases
) : ViewModel() {

    private val getGameUseCase = triviaUseCases.getGameUseCase
    private val updateCurrentGameUseCase = triviaUseCases.updateCurrentGameUseCase
    private val addPlayedGameUseCase = triviaUseCases.addPlayedGameUseCase
    private val finishGameUseCase = triviaUseCases.finishGameUseCase
    private val saveStatsUseCase = triviaUseCases.saveStatsUseCase

    private val _endOfGame = MutableStateFlow(false)
    val endOfGame = _endOfGame.asStateFlow()

    private val _game = MutableStateFlow<Game?>(null)
    val game = _game.asStateFlow()

    private val _time = MutableStateFlow(QUESTION_TIME_MILLIS)
    val time = _time.asStateFlow()

    private var answerIsCorrect = false
    var answer = ""

    private lateinit var timeJob: Job

    init {
        _game.value = getGameUseCase.execute().value
    }

    fun answerQuestion(): Boolean {
        timeJob.cancel()
        game.value?.let {
            answerIsCorrect =
                it.currentQuestion.answer.strip().lowercase() ==
                        answer.lowercase().strip()
        }
        return answerIsCorrect
    }

    fun nextQuestion() {
        if (game.value?.currentQuestionNumber == game.value?.questionsList?.size)
            finishGame()
        else {
            game.value?.let { game ->
                _game.value = updateCurrentGameUseCase.execute(
                    game = game,
                    timeSpent = QUESTION_TIME_MILLIS - _time.value,
                    answerIsCorrect = answerIsCorrect,
                    nextQuestionNumber = game.currentQuestionNumber
                )
            }
            answer = ""
        }
    }

    fun startQuestionTime() {
        _time.value = QUESTION_TIME_MILLIS
        timeJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _time.value -= 1000
                if (_time.value == 0L)
                    break
            }
        }
    }

    private fun finishGame() {
        _endOfGame.value = true
        game.value?.let { game ->
            _game.value = updateCurrentGameUseCase.execute(
                game = game,
                timeSpent = QUESTION_TIME_MILLIS - _time.value,
                answerIsCorrect = answerIsCorrect,
                nextQuestionNumber = game.currentQuestionNumber - 1
            )
            finishGameUseCase.execute()
            viewModelScope.launch {
                addPlayedGameUseCase.execute(game)
                saveStatsUseCase.execute(game)
            }
        }
    }

    class Factory @Inject constructor(
        private val triviaUseCases: TriviaUseCases
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TriviaViewModel(triviaUseCases) as T
        }
    }
}