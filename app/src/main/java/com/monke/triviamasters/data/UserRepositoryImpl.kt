package com.monke.triviamasters.data

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

    private val user = MutableStateFlow<User?>(null)

    override fun getUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmail(email: String): Result  {
        delay(1000)
        return Result.Success(body = mockedUser)
    }
}