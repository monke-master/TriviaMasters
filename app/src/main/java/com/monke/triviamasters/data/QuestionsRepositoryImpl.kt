package com.monke.triviamasters.data

import com.monke.triviamasters.domain.mockedQuestions
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.delay
import java.lang.Integer.min
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(): QuestionRepository {

    override suspend fun getQuestionsBySettings(gameSettings: GameSettings): Result {
        delay(2000)
        var questions = mockedQuestions
        gameSettings.maxPrice?.let { maxPrice ->
            questions = questions.filter { it.price <= maxPrice }
        }
        gameSettings.minPrice?.let { minPrice ->
            questions = questions.filter { it.price >= minPrice }
        }
        gameSettings.selectedCategories?.let { selectedCategories ->
            if (selectedCategories.isNotEmpty())
                questions = questions.filter { it.category in selectedCategories }
        }

        questions = questions.subList(0, min(gameSettings.questionsCount, questions.size))

        return Result.Success(body = questions)
    }

    override suspend fun getRandomQuestions(count: Int): Result {
        delay(2000)

        //val questions = mockedQuestions.subList(0, count)
        return Result.Success(body = mockedQuestions)
    }


}