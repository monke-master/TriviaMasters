package com.monke.triviamasters.domain.useCases

import javax.inject.Inject

class IsPasswordValidUseCase  @Inject constructor() {

    fun execute(password: String, repeatedPassword: String): Boolean {
        return password.isNotEmpty() && repeatedPassword.isNotEmpty() && (password == repeatedPassword)
    }

}