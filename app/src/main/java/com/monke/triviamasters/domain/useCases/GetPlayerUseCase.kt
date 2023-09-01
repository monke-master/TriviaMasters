package com.monke.triviamasters.domain.useCases

import com.monke.triviamasters.domain.repositories.PlayerRepository
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    fun execute() = playerRepository.getPlayer()
}