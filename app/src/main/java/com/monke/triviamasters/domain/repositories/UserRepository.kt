package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun setUser(user: User): Result<Any?>

    fun getUser() : User?

    suspend fun getUserByEmail(email: String): Result<User?>
}