package com.monke.triviamasters.domain.models

data class GameSettings(
    private val gameMode: GameMode,
    private val selectedCategories: List<Category>? = null,
    private val valueMin: Int? = null,
    private val valueMax: Int? = null,
    private val questionsNumber: Int? = null
)
