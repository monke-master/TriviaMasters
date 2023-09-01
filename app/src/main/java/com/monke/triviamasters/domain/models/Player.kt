package com.monke.triviamasters.domain.models

data class Player(
    val username: String,
    val startedPlayingDate: Long,
    val statistics: Statistics
)