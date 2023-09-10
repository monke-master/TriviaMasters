package com.monke.triviamasters.domain.useCases

import com.monke.triviamasters.domain.exceptions.IncorrectPasswordException
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String, password: String) : Result =
        withContext(Dispatchers.IO) {
            val reposResult = userRepository.getUserByEmail(email)
            if (reposResult is Result.Success) {
                val user = reposResult.body as User
                if (user.password == password)
                    return@withContext Result.Success()
                return@withContext Result.Failure(exception = IncorrectPasswordException())
            } else {
                return@withContext reposResult
            }
        }
}