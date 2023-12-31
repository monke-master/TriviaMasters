package com.monke.triviamasters.domain.useCases.email

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import javax.inject.Inject

class SaveEmailUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    fun execute(email: String) {
        registrationRepository.email = email
    }

}