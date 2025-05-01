package com.example.makepizza_android.domain.auth.models

data class FirebaseUserCreate(
    val email: String?,
    val phoneNumber: String?,
    val displayName: String?,
    val photoURL: String?,
    val password: String
)
