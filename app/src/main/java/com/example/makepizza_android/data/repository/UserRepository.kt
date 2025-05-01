package com.example.makepizza_android.data.repository

import com.example.makepizza_android.data.models.UserModel
import com.example.makepizza_android.data.network.MakePizzaAPIService

class UserRepository {
    private val api = MakePizzaAPIService()

    suspend fun getCurrentUser(): UserModel? {
        val response = api.getCurrentUser()
        return response
    }
}