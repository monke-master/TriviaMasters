package com.monke.triviamasters.domain.models

data class GameSettings(
    val gameMode: GameMode,
    val selectedCategories: List<Category>? = null,
    val price: Int? = null,
    val questionsCount: Int = QUESTIONS_COUNT_DEFAULT
)
