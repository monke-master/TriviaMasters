package com.monke.triviamasters.domain.models

data class Game(
    val questionsList: List<Question>,
    val currentQuestion: Question,
    val correctAnswers: Int = 0
)