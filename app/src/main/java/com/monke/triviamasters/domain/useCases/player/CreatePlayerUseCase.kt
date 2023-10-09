package com.monke.triviamasters.domain.useCases.player

import com.monke.triviamasters.di.LoginFragmentScope
import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.models.Statistics
import com.monke.triviamasters.domain.repositories.PlayerRepository
import javax.inject.Inject


class CreatePlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    fun execute(
        startedPlayingDate: Long,
        username: String
    ): Player {
        val player = Player(
            startedPlayingDate = startedPlayingDate,
            username = username,
            statistics = Statistics()
        )
        playerRepository.setPlayer(player)
        return player
    }
}