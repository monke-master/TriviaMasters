package com.monke.triviamasters.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.monke.triviamasters.di.LoginFragmentScope
import com.monke.triviamasters.domain.exceptions.NoUserException
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import java.util.UUID
import javax.inject.Inject

@LoginFragmentScope
class RegistrationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): RegistrationRepository {

    private val emailConfirmed = MutableStateFlow<Boolean>(false)
    override var email : String = ""
    override var password: String = ""
    private lateinit var rawPassword: String

    init {
        Log.d("RegistrationRepository", "init block")
    }

    override suspend fun sendConfirmationLetter(): Result<Any?> {
        emailConfirmed.value = false
        Log.d("RegistrationRepository", "email has been sent")
        try {
            // Создает пробную запись, чтобы потом отправить письмо с подтверждением
            rawPassword = UUID.randomUUID().toString()
            val result = firebaseAuth.createUserWithEmailAndPassword(
                email,
                rawPassword
            ).await()
            val user = result.user ?: return Result.failure(NoUserException())
            user.sendEmailVerification().await()
            return Result.success(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }


    override fun getConfirmationStatus(): StateFlow<Boolean> = emailConfirmed

    override suspend fun createUser(user: User): Result<String> {

        return Result.success(UUID.randomUUID().toString())
    }

    override suspend fun startCheckingConfirmationStatus(): Result<Any?> {
        while (true) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, rawPassword).await()
                val user = firebaseAuth.currentUser ?: return Result.failure(NoUserException())
                if (user.isEmailVerified) {
                    emailConfirmed.value = true
                    return Result.success(null)
                }
                delay(5000)
            } catch (exception: Exception) {
                exception.printStackTrace()
                return Result.failure(exception)
            }

        }
    }


}