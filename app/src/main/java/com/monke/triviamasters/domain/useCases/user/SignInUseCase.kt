package com.monke.triviamasters.domain.useCases.user

import com.monke.triviamasters.domain.exceptions.IncorrectPasswordException
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String, password: String): Result<Any?> =
        withContext(Dispatchers.IO) {
            val reposResult = userRepository.getUserByEmail(email)
            if (reposResult.isSuccess) {
                val user = reposResult.getOrNull() as User
                if (user.password == password) {
                    userRepository.setUser(user)
                    return@withContext reposResult
                }
                return@withContext Result.failure(exception = IncorrectPasswordException())
            } else {
                return@withContext reposResult
            }
        }
}