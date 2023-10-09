package com.monke.triviamasters.data.repository

import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.domain.mockedUser
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor() : UserRepository{

    private var user: User? = null

    override suspend fun setUser(user: User): Result<Any?> {
        this.user = user
        return kotlin.Result.success(null)
    }

    override fun getUser(): User? = user

    override suspend fun getUserByEmail(email: String): Result<User?>  {
        delay(1000)
        return Result.success(mockedUser)
    }
}