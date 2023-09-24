package com.monke.triviamasters.domain.models

data class Game(
    val questionsList: List<Question>,
    val currentQuestionNumber: Int = 1,
    val currentQuestion: Question = questionsList[0],
    val correctAnswers: Int = 0

)