package com.monke.triviamasters.data.repository

import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.domain.mockedUser
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor() : UserRepository{

    private var user: User? = null

    override suspend fun setUser(user: User): kotlin.Result<Any?> {
        this.user = user
        return kotlin.Result.success(null)
    }

    override fun getUser(): User? = user

    override suspend fun getUserByEmail(email: String): Result  {
        delay(1000)
        return Result.Success(body = mockedUser)
    }
}