package com.monke.triviamasters.domain.models

data class GameSettings(
    val gameMode: GameMode,
    val selectedCategories: List<Category>? = null,
    val valueMin: Int? = null,
    val valueMax: Int? = null,
    val questionsNumber: Int? = null
)
