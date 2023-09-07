package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RegistrationRepository {

    fun saveEmail(email: String?)

    suspend fun sendConfirmationLetter(): Result

    fun savePassword(password: String?)

    fun getConfirmationStatus(): StateFlow<Boolean>

    suspend fun createUser(): Result

}