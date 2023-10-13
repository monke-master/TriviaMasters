package com.monke.triviamasters.data.converters

import com.monke.triviamasters.data.remote.dto.UserRemote
import com.monke.triviamasters.domain.models.User

fun UserRemote.toDomain() =
    User(
        email = this.email,
        password = this.password,
        registrationDate = this.registrationDate,
        player = this.player?.toDomain()!!,
        id = this.id
    )