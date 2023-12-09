package com.example.servicemanager.feature_authentication_data.remote

import android.util.Log
import com.example.core.util.Resource
import com.example.core.util.ResourceState
import com.example.core.util.UiText
import com.example.feature_authentication_data.R
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class UserFirebaseApi(
    val firebaseAuth: FirebaseAuth,
) {

    private val USER_FIREBASE_API: String = "USER_FIREBASE_API"

    fun authenticate(mail: String, password: String): Flow<Resource<String>> = flow {
        var userId: String? = null
        try {
            val reference = firebaseAuth.signInWithEmailAndPassword(mail, password)
            val result = reference.await()
            userId = result.user?.uid
            if (result.user?.isEmailVerified == true) {
                emit(
                    Resource(
                        ResourceState.SUCCESS,
                        userId,
                        UiText.StringResource(R.string.authentication_successful)
                    )
                )

            } else {
                emit(
                    Resource(
                        ResourceState.ERROR,
                        userId,
                        UiText.StringResource(R.string.e_mail_not_verified)
                    )
                )
            }
        } catch (e: FirebaseAuthException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    userId,
                    UiText.StringResource(R.string.incorrect_e_mail_or_password)
                )
            )
        } catch (e: FirebaseTooManyRequestsException) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    userId,
                    UiText.StringResource(R.string.to_many_login_attemps_try_again_later)
                )
            )
        }
    }

    fun getCurrentUser(): String? {
        return firebaseAuth.currentUser?.uid
    }
}