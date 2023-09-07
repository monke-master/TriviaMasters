package com.monke.triviamasters.domain.useCases

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import javax.inject.Inject

class SaveEmailUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    fun execute(email: String) {
        registrationRepository.saveEmail(email)
    }

}