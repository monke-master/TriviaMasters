package com.monke.triviamasters.data.remote.dto

import com.monke.triviamasters.domain.models.Statistics

data class PlayerRemote(
    val username: String = "",
    val startedPlayingDate: Long = 0,
    val statistics: Statistics? = null
)