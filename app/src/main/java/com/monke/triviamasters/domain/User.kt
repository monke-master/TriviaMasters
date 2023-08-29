package com.monke.triviamasters.domain

data class User(
    val player: Player,
    val email: String,
    val password: String,
    val registrationDate: Long,
    val id: String
)