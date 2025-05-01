package com.example.makepizza_android.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserModel(
    @SerializedName("uuid") val uid: String,
    val name: String,
    val email: String,
    @SerializedName("is_admin") val isAdmin: Boolean,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)

data class UserCreate(
    val name: String,
    val email: String,
    val password: String
)

data class UserUpdate(
    val name: String,
    val email: String
)