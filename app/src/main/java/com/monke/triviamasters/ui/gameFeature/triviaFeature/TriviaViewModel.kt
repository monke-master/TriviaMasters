package com.monke.triviamasters.ui.gameFeature.triviaFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.QUESTION_TIME_MILLIS
import com.monke.triviamasters.domain.useCases.game.GetGameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TriviaViewModel(
    private val getGameUseCase: GetGameUseCase
) : ViewModel() {

    private val _endOfGame = MutableStateFlow(false)
    val endOfGame = _endOfGame.asStateFlow()

    private val _game = MutableStateFlow<Game?>(null)
    val game = _game.asStateFlow()

    private val _time = MutableStateFlow(QUESTION_TIME_MILLIS)
    val time = _time.asStateFlow()

    var answer = ""

    private lateinit var timeJob: Job

    init {
        _game.value = getGameUseCase.execute().value
    }

    fun answerQuestion(): Boolean {
        timeJob.cancel()
        game.value?.let {
            return it.currentQuestion.answer.strip().lowercase() == answer.lowercase().strip()
        }
        return false
    }

    fun nextQuestion() {
        if (game.value?.currentQuestionNumber == game.value?.questionsList?.size)
            _endOfGame.value = true
        else {
            val mGame = game.value
            mGame?.let {
                _game.value = mGame.copy(
                    currentQuestion = mGame.questionsList[mGame.currentQuestionNumber],
                    currentQuestionNumber = mGame.currentQuestionNumber + 1
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