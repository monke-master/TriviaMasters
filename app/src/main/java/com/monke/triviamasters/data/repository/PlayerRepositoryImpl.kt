package com.monke.triviamasters.data.repository

import com.monke.triviamasters.data.converters.toRoom
import com.monke.triviamasters.data.room.dao.PlayerDao
import com.monke.triviamasters.data.room.model.PlayerRoom
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.models.Statistics
import com.monke.triviamasters.domain.repositories.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AppScope
class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao
): PlayerRepository {

    private var player: MutableStateFlow<Player?> = MutableStateFlow(null)

    override suspend fun setPlayer(player: Player?) {
        player?.let {
            playerDao.addPlayer(it.toRoom())
        }
        this.player.value = player
    }

    override fun getPlayer(): MutableStateFlow<Player?> = player

}