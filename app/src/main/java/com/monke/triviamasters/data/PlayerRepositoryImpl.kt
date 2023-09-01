package com.monke.triviamasters.data

import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(): PlayerRepository {

    private var player: MutableStateFlow<Player?> = MutableStateFlow(null)

    override fun setPlayer(player: Player?) {
        this.player.value = player
    }

    override fun getPlayer(): MutableStateFlow<Player?> = player


}