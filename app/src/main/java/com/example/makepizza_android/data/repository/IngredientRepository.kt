package com.example.makepizza_android.data.repository

import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.network.MakePizzaAPIService

class IngredientRepository {
    private val api = MakePizzaAPIService()

    suspend fun getAllIngredients(): List<IngredientListModel> {
        val response = api.getAllIngredients()
        return response
    }
}