package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.User

interface UserRepository {

    suspend fun updateUser(user: User): Result<Any?>

    fun getUser() : User?

    suspend fun getUserByEmail(email: String): Result<User?>

    suspend fun signIn(email: String, password: String): Result<User?>

    suspend fun createUser(user: User): Result<Any?>
}