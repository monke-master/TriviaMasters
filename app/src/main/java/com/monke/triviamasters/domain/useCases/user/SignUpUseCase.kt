package com.monke.triviamasters.domain.useCases.user


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
    private val playerRepository: PlayerRepository
) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        playerRepository.getPlayer().first()?.let {  player ->
            val user = User(
                id = UUID.randomUUID().toString(),
                player = player,
                email = registrationRepository.email,
                password = registrationRepository.password,
                registrationDate = Calendar.getInstance().timeInMillis
            )
            val createRequest = registrationRepository.createUser(user)
            if (createRequest.isSuccess)
                userRepository.setUser(user)
            return@withContext createRequest
        }
    }
}