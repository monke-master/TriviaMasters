package com.monke.triviamasters.domain.useCases

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import javax.inject.Inject

class GetConfirmationStatusUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    fun execute() = registrationRepository.getConfirmationStatus()
}