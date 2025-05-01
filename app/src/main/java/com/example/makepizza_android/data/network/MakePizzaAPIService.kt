package com.example.makepizza_android.data.network

import com.example.makepizza_android.core.MakePizzaAPI
import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MakePizzaAPIService {
    private val retrofit = MakePizzaAPI.getRetrofit()

    suspend fun getCurrentUser(): UserModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getCurrentUser()
            response.body()
        }
    }

    suspend fun getAllIngredients(): List<IngredientListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllIngredients()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllPizzas(): List<PizzaListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllPizzas()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAllPizzasFromUser(): List<PizzaListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllPizzasFromUser()
            response.body() ?: emptyList()
        }
    }
}