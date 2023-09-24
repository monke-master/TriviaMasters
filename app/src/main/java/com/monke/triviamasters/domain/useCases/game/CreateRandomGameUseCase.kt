package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class CreateRandomGameUseCase @Inject constructor(
    private val questionRepository: QuestionRepository
) {

    suspend fun execute(): Result = withContext(Dispatchers.IO) {
        val count = Random.nextInt(1, 101)
        questionRepository.getRandomQuestions(count)
    }

}