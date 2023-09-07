package com.monke.triviamasters.domain.models

import java.lang.Exception

sealed class Result {

    data class Success(
        val body: Any?
    ): Result()

    data class Failure(
        val exception: Exception
    ): Result()
}
