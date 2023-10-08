package com.monke.triviamasters.data.repository

import com.monke.triviamasters.data.converters.toDomain
import com.monke.triviamasters.data.remote.TriviaApi
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.exceptions.RepositoryException
import com.monke.triviamasters.domain.mockedQuestions
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.delay
import java.lang.Exception
import java.lang.Integer.min
import javax.inject.Inject

@GameFragmentScope
class QuestionsRepositoryImpl @Inject constructor(
    private val apiDataSource: TriviaApi
): QuestionRepository {

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

    override suspend fun getRandomQuestions(count: Int): kotlin.Result<List<Question>> {
        try {
            val request = apiDataSource.getRandomQuestions(count)
            if (request.isSuccessful)
                return kotlin.Result.success(
                    request.body()?.map { it.toDomain() } ?: ArrayList())
            return kotlin.Result.failure(
                RepositoryException(
                    message = request.message(),
                    code = request.code()
                )
            )
        } catch (e: Exception) {
            return kotlin.Result.failure(e)
        }
    }

    override suspend fun getQuestionsBySettings(
        count: Int?,
        offset: Int?,
        categoryId: Int?,
        value: Int?
    ): kotlin.Result<List<Question>> {
        try {
            val request = apiDataSource.getQuestionsBySettings(
                value = value,
                offset = offset,
                categoryId = categoryId
            )
            if (request.isSuccessful) {
                var questionsList = request.body()
                count?.let { questionsList = questionsList?.subList(0, count)}
                return kotlin.Result.success(
                    questionsList?.map { it.toDomain() } ?: ArrayList())
            }
            return kotlin.Result.failure(
                RepositoryException(
                    message = request.message(),
                    code = request.code()
                )
            )
        } catch (e: Exception) {
            return kotlin.Result.failure(e)
        }
    }

}