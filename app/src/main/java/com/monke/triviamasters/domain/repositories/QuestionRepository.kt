package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Question

interface QuestionRepository {


    suspend fun getRandomQuestions(count: Int): Result<List<Question>>

    suspend fun getQuestionsBySettings(
        count: Int? = null,
        offset: Int? = null,
        categoryId: Int? = null,
        value: Int? = null
    ): Result<List<Question>>

}