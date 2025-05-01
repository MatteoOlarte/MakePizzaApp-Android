package com.example.makepizza_android.domain.auth

import com.example.makepizza_android.domain.auth.models.FirebaseUserCreate
import com.example.makepizza_android.domain.auth.models.FirebaseUserLogin
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface IFirebaseAuth {
    fun createUser(data: FirebaseUserCreate): Task<AuthResult>

    fun loginUser(data: FirebaseUserLogin): Task<AuthResult>
}

