package com.monke.triviamasters.domain.models

data class Statistics(
    val score: Long = 0,
    val triviaCompleted: Int = 0,
    val correctAnswers: Int = 0,
    val questionsAnswered: Int = 0
)
