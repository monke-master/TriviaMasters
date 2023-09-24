package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.GameRepository
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class CreateExtraHardGameUseCase @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val gameRepository: GameRepository
) {

    suspend fun execute(): Result = withContext(Dispatchers.IO) {
        val count = Random.nextInt(1, 100)
        val repoResult = questionRepository.getHardestQuestions(count)
        if (repoResult is Result.Failure)
            return@withContext repoResult
        val questions = (repoResult as Result.Success).body as List<Question>
        gameRepository.createGame(Game(questionsList = questions))
        return@withContext Result.Success()
    }
}