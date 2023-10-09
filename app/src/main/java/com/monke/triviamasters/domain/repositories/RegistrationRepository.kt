package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.flow.StateFlow

interface RegistrationRepository {

    var email: String

    var password: String

    suspend fun sendConfirmationLetter(): Result<Any?>


    fun getConfirmationStatus(): StateFlow<Boolean>


    suspend fun signUp(user: User): Result<String?>


    suspend fun startCheckingConfirmationStatus(): Result<Any?>
}