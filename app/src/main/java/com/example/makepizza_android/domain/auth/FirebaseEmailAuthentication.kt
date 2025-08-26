package com.example.makepizza_android.domain.auth

import com.example.makepizza_android.domain.auth.models.FirebaseUserCreate
import com.example.makepizza_android.domain.auth.models.FirebaseUserLogin
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth

class

















FirebaseEmailAuthentication: IFirebaseAuth {
    override fun createUser(data: FirebaseUserCreate): Task<AuthResult> {
        val auth = Firebase.auth
        val email = data.email
        val password = data.password

        if (email == null) {
            val result = TaskCompletionSource<AuthResult>()
            result.setException(Exception("Email cannot be null"))
            return result.task
        }
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun loginUser(data: FirebaseUserLogin): Task<AuthResult> {
        val auth = Firebase.auth
        return auth.signInWithEmailAndPassword(data.username, data.password)
    }
}