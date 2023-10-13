package com.monke.triviamasters.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.monke.triviamasters.data.room.model.PlayerRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert
    suspend fun addPlayer(playerRoom: PlayerRoom)

    @Query("SELECT * FROM players WHERE authorized == 1")
    fun getAuthorizedPlayer(): Flow<PlayerRoom>

    @Query("SELECT * FROM players")
    fun getPlayers(): Flow<List<PlayerRoom>>
}