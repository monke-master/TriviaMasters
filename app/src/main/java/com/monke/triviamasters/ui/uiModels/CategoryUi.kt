package com.monke.triviamasters.ui.uiModels

import com.monke.triviamasters.domain.models.Category

data class CategoryUi(
    val category: Category,
    val isSelected: Boolean
)