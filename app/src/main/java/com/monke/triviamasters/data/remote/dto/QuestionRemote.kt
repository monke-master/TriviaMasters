package com.monke.triviamasters.data.remote.dto

data class QuestionRemote(
    val id: Int,
    val answer: String,
    val question: String,
    val value: Int,
    val categoryId: Int,
    val category: CategoryRemote
)