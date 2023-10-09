package com.monke.triviamasters.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.monke.triviamasters.domain.models.User
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class UserFirestore @Inject constructor(
    private val firestore: FirebaseFirestore
) {


    suspend fun createUser(user: User): Result<Any?> {
        try {
            val hashMap = hashMapOf<String, Any>(
                "email" to user.email
            )
            firestore.collection(USERS_COLLECTION).add(hashMap).await()
            return Result.success(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Result.failure(exception)
        }
    }

}