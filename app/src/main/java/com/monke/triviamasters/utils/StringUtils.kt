package com.monke.triviamasters.utils

import com.monke.triviamasters.domain.models.Category

fun Category.toBundleString() = "${this.id};${this.title}"

fun Category.Companion.fromBundleString(string: String) =
    Category(
        id = string.split(";")[0].toInt(),
        title = string.split(";")[1]
    )