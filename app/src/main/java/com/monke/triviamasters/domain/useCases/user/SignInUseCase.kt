package com.monke.triviamasters.domain.useCases.user

import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.PlayerRepository
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository
) {

    suspend fun execute(email: String, password: String): Result<Any?> =
        withContext(Dispatchers.IO) {
            val reposResult = userRepository.signIn(email, password)
            if (reposResult.isSuccess) {
                val user = reposResult.getOrNull() as User
                userRepository.setUser(user)
                playerRepository.setPlayer(user.player)
                return@withContext reposResult
            } else {
                return@withContext reposResult
            }
        }
}