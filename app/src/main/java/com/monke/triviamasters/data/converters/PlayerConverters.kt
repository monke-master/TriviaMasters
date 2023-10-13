package com.monke.triviamasters.data.converters

import com.monke.triviamasters.data.remote.dto.PlayerRemote
import com.monke.triviamasters.data.room.model.PlayerRoom
import com.monke.triviamasters.domain.models.Player


fun PlayerRemote.toDomain() =
    Player(
        username = username,
        startedPlayingDate = startedPlayingDate,
        statistics = statistics!!
    )


fun Player.toRoom() =
    PlayerRoom(
        username = username,
        startedPlayingDate = startedPlayingDate,
        statistics = statistics,
    )