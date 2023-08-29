package com.monke.triviamasters.domain

data class Statistics(
    val score: Long,
    val triviaCompleted: Int,
    val rightAnswers: Int,
    val questionsAnswered: Int
)
