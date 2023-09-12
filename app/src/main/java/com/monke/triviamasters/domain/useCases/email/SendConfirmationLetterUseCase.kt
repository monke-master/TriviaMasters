package com.monke.triviamasters.domain.useCases.email

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import javax.inject.Inject

class SendConfirmationLetterUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    suspend fun execute() = registrationRepository.sendConfirmationLetter()
}