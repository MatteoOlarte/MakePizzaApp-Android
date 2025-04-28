package com.example.makepizza_android.data.network

import com.example.makepizza_android.core.MakePizzaAPI
import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.models.PizzaListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MakePizzaAPIService {
    private val retrofit = MakePizzaAPI.getRetrofit()

    suspend fun getAllPizzas(): List<PizzaListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllPizzas()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllIngredients(): List<IngredientListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllIngredients()
            response.body() ?: emptyList()
        }
    }
}