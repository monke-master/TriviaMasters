package com.monke.triviamasters.domain.useCases.player

import com.monke.triviamasters.domain.models.Game
import com.monke.triviamasters.domain.repositories.PlayerRepository
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveStatsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository
) {

    suspend fun execute(playedGame: Game): Result<Any?> {
        val player = playerRepository.getPlayer().first()
        player?.let {
            val oldStats = player.statistics
            val newStats = oldStats.copy(
                score = oldStats.score + playedGame.pointsEarned,
                gamesPlayed = oldStats.gamesPlayed + 1,
                correctAnswers = oldStats.correctAnswers + playedGame.correctAnswers,
                questionsAnswered = oldStats.questionsAnswered + playedGame.questionsList.size
            )
            val newPlayer = player.copy(statistics = newStats)
            playerRepository.setPlayer(newPlayer)
            userRepository.getUser()?.let {
                userRepository.setUser(it.copy(player = newPlayer))
            }
        }
        return Result.success(null)

    }
}