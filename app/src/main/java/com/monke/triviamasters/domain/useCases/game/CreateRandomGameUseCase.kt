package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.GameRepository
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class CreateRandomGameUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val gameRepository: GameRepository
) {

    suspend fun execute(): Result = withContext(Dispatchers.IO) {
        val count = Random.nextInt(1, 101)
        val repoResult = questionRepository.getRandomQuestions(count)
        if (repoResult is Result.Failure)
            return@withContext repoResult
        val questions = (repoResult as Result.Success).body as List<Question>
        val game = Game(questionsList = questions)
        gameRepository.createGame(game)
        return@withContext Result.Success()
    }

}