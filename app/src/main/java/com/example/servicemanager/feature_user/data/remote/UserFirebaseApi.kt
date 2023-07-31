package com.example.servicemanager.feature_user.data.remote

import android.util.Log
import com.example.servicemanager.core.util.Resource
import com.example.servicemanager.core.util.ResourceState
import com.example.servicemanager.feature_user.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserFirebaseApi(
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore
) {

    private val USER_FIREBASE_API: String = "USER_FIREBASE_API"

    fun authenticate(mail: String, password: String): Flow<Resource<String>> = flow {
        var userUid: String? = null
        emit(Resource(ResourceState.LOADING, userUid, "Authentication started"))
        val reference = firebaseAuth.signInWithEmailAndPassword(mail, password)
        val result = reference.await()
        userUid = result.user?.uid
        if (result.user?.isEmailVerified == true) {
            Log.d(USER_FIREBASE_API, "Authentication successful")
            emit(Resource(ResourceState.SUCCESS, userUid, "Authentication successful"))

        } else {
            Log.d(USER_FIREBASE_API, "Authentication failure")
            emit(Resource(ResourceState.ERROR, userUid, "Authentication failure"))
        }
    }

    suspend fun getUser(userId: String): User? {
        Log.d(USER_FIREBASE_API,  "User fetching started")
        var user: User? = null
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
}