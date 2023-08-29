package com.monke.triviamasters.domain

data class Question(
    val title: String,
    val answer: String,
    val difficulty: Int,
    val category: Category
)