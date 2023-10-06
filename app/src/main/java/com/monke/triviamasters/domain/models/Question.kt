package com.monke.triviamasters.domain.models

data class Question(
    val id: Int,
    val question: String,
    val answer: String,
    val price: Int,
    val category: Category
)