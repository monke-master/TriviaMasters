package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.exceptions.NoQuestionsException
import com.monke.triviamasters.domain.models.Game
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

    suspend fun execute(): kotlin.Result<Any?> = withContext(Dispatchers.IO) {
        val count = Random.nextInt(1, 101)
        val repoResult = questionRepository.getRandomQuestions(count)
        if (repoResult.isFailure)
            return@withContext repoResult
        val questions = repoResult.getOrNull()
        questions?.let {
            val game = Game(questionsList = questions)
            gameRepository.createGame(game)
            return@withContext kotlin.Result.success(null)
        }
        return@withContext kotlin.Result.failure(NoQuestionsException())

    }

}