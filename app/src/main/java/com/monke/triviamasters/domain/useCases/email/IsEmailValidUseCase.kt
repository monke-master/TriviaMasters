package com.monke.triviamasters.domain.useCases.email

import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor() {

    fun execute(email: String): Boolean {
        return email.isNotEmpty()
    }

}