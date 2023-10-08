package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.exceptions.NoQuestionsException
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.MAX_EXTRA_HARD_GAME_OFFSET
import com.monke.triviamasters.domain.models.MAX_QUESTIONS_COUNT
import com.monke.triviamasters.domain.models.MAX_QUESTION_PRICE
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

    suspend fun execute(): Result<Any?> = withContext(Dispatchers.IO) {
        val count = Random.nextInt(1, MAX_QUESTIONS_COUNT)
        val offset = Random.nextInt(0, MAX_EXTRA_HARD_GAME_OFFSET)
        val repoResult = questionRepository.getQuestionsBySettings(
            count = count,
            offset = offset,
            value = MAX_QUESTION_PRICE
        )
        if (repoResult.isFailure)
            return@withContext repoResult
        val body = repoResult.getOrNull()
        if (body.isNullOrEmpty())
            return@withContext Result.failure(NoQuestionsException())
        gameRepository.createGame(Game(questionsList = body))
        return@withContext Result.success(null)
    }
}