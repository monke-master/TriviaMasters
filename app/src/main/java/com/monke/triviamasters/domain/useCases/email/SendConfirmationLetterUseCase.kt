package com.monke.triviamasters.domain.useCases.email

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendConfirmationLetterUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    suspend fun execute(): Result<Any?> = withContext(Dispatchers.IO) {
        val result = registrationRepository.sendConfirmationLetter()
        if (result.isFailure)
            return@withContext result
        return@withContext registrationRepository.startCheckingConfirmationStatus()
    }
}