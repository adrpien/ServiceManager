package com.example.servicemanager.feature_authentication.data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.servicemanager.feature_authentication.domain.model.User
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserFirebaseApi(
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore
) {

    private val USER_FIREBASE_API: String = "USER_FIREBASE_API"

    fun authenticate(mail: String, password: String): Flow<com.example.core.util.Resource<String>> = flow {
        var userId: String? = "0"
        emit(
            com.example.core.util.Resource(
                com.example.core.util.ResourceState.LOADING,
                userId,
                "Authentication started"
            )
        )
        try {
            val reference = firebaseAuth.signInWithEmailAndPassword(mail, password)
            val result = reference.await()
            userId = result.user?.uid
            if (result.user?.isEmailVerified == true) {
                Log.d(USER_FIREBASE_API, "Authentication successful")
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.SUCCESS,
                        userId,
                        "Authentication successful"
                    )
                )

            } else {
                Log.d(USER_FIREBASE_API, "E-mail not verified")
                emit(
                    com.example.core.util.Resource(
                        com.example.core.util.ResourceState.ERROR,
                        "E-mail not verified",
                        "E-mail not verified"
                    )
                )
            }
        } catch (e: FirebaseAuthException) {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    "Incorrect e-mail or password",
                    "Incorrect e-mail or password"
                )
            )
        } catch (e: FirebaseTooManyRequestsException) {
            emit(
                com.example.core.util.Resource(
                    com.example.core.util.ResourceState.ERROR,
                    "To many login attemps, try again later",
                    "To many login attemps, try again later"
                )
            )
        }
    }

    suspend fun getUser(userId: String): User? {
        Log.d(USER_FIREBASE_API,  "User fetching started")
        var user: User? = User()
        val documentReference = firebaseFirestore.collection("users").document(userId)
        val result = documentReference.get()
        result.await()
        if(result.isSuccessful) {
            Log.d(USER_FIREBASE_API,  "User fetched")
            user = result.result.toObject(User::class.java)
        } else {
            Log.d(USER_FIREBASE_API,  "User fetch error")
        }
        return user
    }

    suspend fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }
}