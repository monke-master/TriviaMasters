package com.monke.triviamasters.data.remote.dto


data class UserRemote(
    val id: String = "",
    val player: PlayerRemote? = null,
    val email: String = "",
    val password: String = "",
    val registrationDate: Long = 0,
)