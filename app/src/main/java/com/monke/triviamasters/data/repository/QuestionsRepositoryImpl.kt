package com.monke.triviamasters.data.repository

import android.util.Log
import com.monke.triviamasters.data.converters.toDomain
import com.monke.triviamasters.data.remote.api.TriviaApi
import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.repositories.QuestionRepository
import retrofit2.HttpException
import java.lang.Exception
import java.lang.Integer.min
import javax.inject.Inject

@GameFragmentScope
class QuestionsRepositoryImpl @Inject constructor(
    private val apiDataSource: TriviaApi
): QuestionRepository {


    override suspend fun getRandomQuestions(count: Int): kotlin.Result<List<Question>> {
        try {
            val response = apiDataSource.getRandomQuestions(count)
            if (response.isSuccessful)
                return kotlin.Result.success(
                    response.body()?.map { it.toDomain() } ?: ArrayList())
            return kotlin.Result.failure(HttpException(response))
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
            val response = apiDataSource.getQuestionsBySettings(
                value = value,
                offset = offset,
                categoryId = categoryId
            )
            if (response.isSuccessful) {
                var questionsList = response.body()
                if (count != null && questionsList != null) {
                    questionsList = questionsList?.subList(0, min(count, questionsList.size))
                }
                return kotlin.Result.success(
                    questionsList?.map { it.toDomain() } ?: ArrayList())
            }
            Log.d("Question repository", "${response.message()} ${response.code()}")
            return kotlin.Result.failure(HttpException(response))
        } catch (e: Exception) {
            e.printStackTrace()
            return kotlin.Result.failure(e)
        }
    }

}