package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface PlayerRepository {

    fun setPlayer(player: Player?)

    fun getPlayer(): Flow<Player?>
}