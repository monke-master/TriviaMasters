package com.monke.triviamasters.domain.repositories

import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RegistrationRepository {

    var email: String

    var password: String

    suspend fun sendConfirmationLetter(): Result


    fun getConfirmationStatus(): StateFlow<Boolean>


    suspend fun createUser(user: User): kotlin.Result<String>

}