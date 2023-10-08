package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.exceptions.NoQuestionsException
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.MAX_QUESTIONS_OFFSET
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.repositories.GameRepository
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


/**
 * Создание игры с заданными настройками
 */
class CreateGameWithSettingsUseCase @Inject constructor(
    private val gameRepository: GameRepository,
    private val questionRepository: QuestionRepository
) {


    suspend fun execute(gameSettings: GameSettings): Result<Any?> {
        return withContext(Dispatchers.IO) {
            // Поиск вопросов по заданным настройкам без категории
            val selectedCategories = gameSettings.selectedCategories
            if (selectedCategories == null) {
               return@withContext createGameWithoutCategories(gameSettings)
            } else {
                return@withContext createGameWithCategories(gameSettings)
            }
        }
    }

    // Поиск вопросов с категориями
    private suspend fun createGameWithCategories(gameSettings: GameSettings): Result<Any?> {
        // Поиск вопросов по заданным настройкам с категориями
        val selectedCategories = gameSettings.selectedCategories!!
        val categoryQuestionsAmount = gameSettings.questionsCount / selectedCategories.size
        val questionsList = ArrayList<Question>()
        for (category in selectedCategories) {
            val response = questionRepository.getQuestionsBySettings(
                count = categoryQuestionsAmount,
                value = gameSettings.price,
                categoryId = category.id
            )
            if (response.isFailure) {
                return response
            }
            response.getOrNull()?.let { questionsList.addAll(it) }
        }
        saveGame(questionsList)
        return Result.success(null)
    }

    // Поиск вопросов без категорий
    private suspend fun createGameWithoutCategories(gameSettings: GameSettings): Result<Any?> {
        val response = questionRepository.getQuestionsBySettings(
            count = gameSettings.questionsCount,
            value = gameSettings.price,
            offset = Random.nextInt(0, MAX_QUESTIONS_OFFSET)
        )
        if (response.isFailure) {
            return response
        }
        val questionsList = response.getOrNull()
        if (questionsList.isNullOrEmpty())
            return Result.failure(NoQuestionsException())
        saveGame(questionsList)
        return Result.success(null)
    }

    private fun saveGame(questionsList: List<Question>) {
        // Создание игры
        val game = Game(questionsList = questionsList)
        gameRepository.createGame(game)
    }
}