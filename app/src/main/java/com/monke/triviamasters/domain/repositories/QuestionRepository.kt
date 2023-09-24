package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Result

interface QuestionRepository {

    suspend fun getQuestionsBySettings(gameSettings: GameSettings): Result

    suspend fun getRandomQuestions(count: Int): Result

}