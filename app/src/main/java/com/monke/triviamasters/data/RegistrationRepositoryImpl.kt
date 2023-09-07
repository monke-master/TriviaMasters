package com.monke.triviamasters.data

import android.util.Log
import com.monke.triviamasters.di.LoginFragmentScope
import com.monke.triviamasters.domain.models.Result
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@LoginFragmentScope
class RegistrationRepositoryImpl @Inject constructor(): RegistrationRepository {

    private val emailConfirmed = MutableStateFlow<Boolean>(false)
    private var email : String? = null
    private var password: String? = null

    init {
        Log.d("RegistrationRepository", "init block")
    }


    override fun saveEmail(email: String?) {
        this.email = email
    }

    override suspend fun sendConfirmationLetter(): Result {
        Log.d("RegistrationRepository", "email has been sent")
        withContext(Dispatchers.IO) {
            delay(2000)
            emailConfirmed.value = true
        }

        return Result.Success(null)
    }

    override fun savePassword(password: String?) {
        this.password = password
    }

    override fun getConfirmationStatus(): StateFlow<Boolean> = emailConfirmed

    override suspend fun createUser(): Result {
        TODO("Not yet implemented")
    }


}