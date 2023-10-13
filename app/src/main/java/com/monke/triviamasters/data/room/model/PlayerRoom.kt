package com.monke.triviamasters.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monke.triviamasters.domain.models.Statistics

@Entity(tableName = "players")
data class PlayerRoom(
    @PrimaryKey(autoGenerate = true) val playerId: Int = 0,
    val username: String,
    val startedPlayingDate: Long,
    @Embedded val statistics: Statistics,
    val authorized: Boolean = true
)