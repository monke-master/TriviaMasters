package com.monke.triviamasters.domain.models

data class Category(
    val id: Int,
    val title: String,
    val questionsCount: Int = 0
)