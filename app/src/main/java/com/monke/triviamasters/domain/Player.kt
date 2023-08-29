package com.monke.triviamasters.domain

data class Player(
    val username: String,
    val startedPlayingDate: Long,
    val statistics: Statistics
)