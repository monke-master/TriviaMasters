package com.monke.triviamasters.domain.useCases.game

import com.monke.triviamasters.domain.exceptions.NoQuestionsException
import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.models.GameSettings
import com.monke.triviamasters.domain.models.Question
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.GameRepository
import com.monke.triviamasters.domain.repositories.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Создание игры с заданными настройками
 */
class CreateGameUseCase @Inject constructor(
    private val gameRepository: GameRepository,
    private val questionRepository: QuestionRepository
) {

    suspend fun execute(gameSettings: GameSettings): Result {
        return withContext(Dispatchers.IO) {
            // Поиск вопросов по заданным настройкам
            val repositoryRes = questionRepository.getQuestionsBySettings(gameSettings)
            // Возвращает ошибку в случае неудачи
            if (repositoryRes is Result.Failure)
                return@withContext repositoryRes
            val questionsList = (repositoryRes as Result.Success).body as List<Question>
            // Возвращает ошибку, если не существует искомых вопросов
            if (questionsList.isEmpty())
                return@withContext Result.Failure(NoQuestionsException())
            // Создание игры
            val game = Game(
                questionsList = questionsList,
                currentQuestion = questionsList[0]
            )
            gameRepository.createGame(game)
            return@withContext Result.Success()
        }
    }
}