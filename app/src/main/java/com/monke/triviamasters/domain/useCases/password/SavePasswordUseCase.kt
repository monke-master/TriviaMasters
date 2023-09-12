package com.monke.triviamasters.domain.useCases.password

import com.monke.triviamasters.domain.repositories.RegistrationRepository
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
){

    fun execute(password: String) {
        registrationRepository.savePassword(password)
    }
}