package com.monke.triviamasters.domain.useCases.trivia

import com.monke.triviamasters.domain.models.Game
import javax.inject.Inject

class UpdateCurrentGameUseCase @Inject constructor() {

    fun execute(
        game: Game,
        answerIsCorrect: Boolean,
        timeSpent: Long,
        nextQuestionNumber: Int
    ): Game {
        var pointsEarned = 0
        if (answerIsCorrect)
            pointsEarned += game.currentQuestion.price
        return game.copy(
            currentQuestion = game.questionsList[nextQuestionNumber],
            currentQuestionNumber = game.currentQuestionNumber + 1,
            timeSpent = game.timeSpent + timeSpent,
            pointsEarned = game.pointsEarned + pointsEarned
        )
    }
}