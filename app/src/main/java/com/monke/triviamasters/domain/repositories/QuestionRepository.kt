package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.data.remote.dto.QuestionRemote
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Result

interface QuestionRepository {

    suspend fun getQuestionsBySettings(gameSettings: GameSettings): Result

    suspend fun getRandomQuestions(count: Int): kotlin.Result<List<Question>>

    suspend fun getQuestionsBySettings(
        count: Int? = null,
        offset: Int? = null,
        categoryId: Int? = null,
        value: Int? = null
    ): kotlin.Result<List<Question>>

}