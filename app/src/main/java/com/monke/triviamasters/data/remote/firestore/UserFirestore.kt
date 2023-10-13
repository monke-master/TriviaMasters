package com.monke.triviamasters.data.remote.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.monke.triviamasters.data.converters.toDomain
import com.monke.triviamasters.data.remote.dto.UserRemote
import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class UserFirestore @Inject constructor(
    private val firestore: FirebaseFirestore
) {


    suspend fun setUser(user: User): Result<Any?> {
        try {
            firestore.collection(USERS_COLLECTION).document(user.id).set(user).await()
            return Result.success(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }

    suspend fun getUserById(id: String): Result<User?> {
        try {
            val response = firestore.collection(USERS_COLLECTION).document(id).get().await()
            val user = response.toObject<UserRemote>()
            return Result.success(user?.toDomain())
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }

}