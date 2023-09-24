package com.monke.triviamasters.domain.models

data class Game(
    val questionsList: List<Question>,
    val currentQuestion: Question = questionsList[0],
    val correctAnswers: Int = 0
)