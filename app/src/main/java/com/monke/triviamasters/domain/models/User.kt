package com.monke.triviamasters.domain.models

data class User(
    val id: String,
    val player: Player,
    val email: String,
    val password: String,
    val registrationDate: Long,
)