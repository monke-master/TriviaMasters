package com.monke.triviamasters.data

import android.util.Log
import com.monke.triviamasters.di.LoginFragmentScope
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@LoginFragmentScope
class RegistrationRepositoryImpl @Inject constructor(): RegistrationRepository {

    private val emailConfirmed = MutableStateFlow<Boolean>(false)
    override var email : String = ""
    override var password: String = ""

    init {
        Log.d("RegistrationRepository", "init block")
    }

    override suspend fun sendConfirmationLetter(): Result {
        emailConfirmed.value = false
        Log.d("RegistrationRepository", "email has been sent")
        withContext(Dispatchers.IO) {
            emailConfirmed.value = true
        }

        return Result.Success(null)
    }

    override fun getConfirmationStatus(): StateFlow<Boolean> = emailConfirmed

    override suspend fun createUser(user: User): kotlin.Result<String> {
        return kotlin.Result.success(UUID.randomUUID().toString())
    }




}