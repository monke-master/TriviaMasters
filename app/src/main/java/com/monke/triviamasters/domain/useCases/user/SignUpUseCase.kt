package com.monke.triviamasters.domain.useCases.user


import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.PlayerRepository
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val registrationRepository: RegistrationRepository,
) {

    suspend fun execute(player: Player): Result<Any?> = withContext(Dispatchers.IO) {
        val user = User(
            id = UUID.randomUUID().toString(),
            player = player,
            email = registrationRepository.email,
            password = registrationRepository.password,
            registrationDate = Calendar.getInstance().timeInMillis
        )
        val createRequest = registrationRepository.signUp(user)
        if (createRequest.isFailure)
            return@withContext createRequest
        return@withContext createUser(user)
    }

    private suspend fun createUser(user: User): Result<Any?> {
        val result = userRepository.createUser(user)
        if (result.isFailure)
            return result
        userRepository.setUser(user)
        return result
    }
}