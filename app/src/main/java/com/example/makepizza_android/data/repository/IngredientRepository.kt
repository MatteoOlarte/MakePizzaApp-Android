package com.example.makepizza_android.data.repository

import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.network.MakePizzaAPIService

class IngredientRepository {
    private val api = MakePizzaAPIService()

    suspend fun getAllIngredients(): List<IngredientListModel> {
        val response = api.getAllIngredients()
        return response
    }
}