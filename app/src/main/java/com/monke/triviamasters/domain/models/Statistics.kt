package com.monke.triviamasters.domain.models

data class Statistics(
    val score: Int = 0,
    val gamesPlayed: Int = 0,
    val correctAnswers: Int = 0,
    val questionsAnswered: Int = 0
)
