package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser() : Flow<User?>

    suspend fun getUserByEmail(email: String): Result
}