package com.monke.triviamasters.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.monke.triviamasters.data.remote.firestore.UserFirestore
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.domain.exceptions.NoUserException
import com.monke.triviamasters.domain.mockedUser
import com.monke.triviamasters.domain.models.User
import com.monke.triviamasters.domain.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AppScope
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: UserFirestore
) : UserRepository{

    private var user: User? = null

    override suspend fun updateUser(user: User): Result<Any?> {
        this.user = user
        try {
            return firestore.setUser(user)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }

    override fun getUser(): User? = user

    override suspend fun getUserByEmail(email: String): Result<User?>  {
        delay(1000)
        return Result.success(mockedUser)
    }

    override suspend fun signIn(email: String, password: String): Result<User?> {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result?.user?.let {
                return firestore.getUserById(it.uid)
            }
            return Result.failure(NoUserException())
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }

    override suspend fun createUser(user: User): Result<Any?> {
        return firestore.setUser(user)
    }

}