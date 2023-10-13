package com.monke.triviamasters.data.converters

import com.monke.triviamasters.data.remote.dto.QuestionRemote
import com.monke.triviamasters.domain.models.Question

fun QuestionRemote.toDomain() =
    Question(
        id = this.id,
        question = this.question,
        answer = this.answer,
        price = this.value,
        category = this.category.toDomain()
    )