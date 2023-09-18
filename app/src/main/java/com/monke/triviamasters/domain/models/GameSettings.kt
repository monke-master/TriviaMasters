package com.monke.triviamasters.domain.models

data class GameSettings(
    val gameMode: GameMode,
    val selectedCategories: List<Category>? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val questionsAmount: Int = QUESTIONS_AMOUNT_DEFAULT
)
