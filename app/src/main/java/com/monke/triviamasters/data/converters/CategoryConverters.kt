package com.monke.triviamasters.data.converters

import com.monke.triviamasters.data.remote.dto.CategoryRemote
import com.monke.triviamasters.domain.models.Category

fun CategoryRemote.toDomain() =
    Category(
        id = this.id,
        title = this.title,
        questionsCount = this.clues_count
    )

