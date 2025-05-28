package com.example.makepizza_android.data.remote.network

import com.example.makepizza_android.core.MakePizzaAPI
import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.models.OrderCreate
import com.example.makepizza_android.data.remote.models.OrderListModel
import com.example.makepizza_android.data.remote.models.OrderModel
import com.example.makepizza_android.data.remote.models.OrderUpdate
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.data.remote.models.PizzaModel
import com.example.makepizza_android.data.remote.models.UserCreate
import com.example.makepizza_android.data.remote.models.UserModel
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

    suspend fun signUp(userCreate: UserCreate): UserModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).signUp(userCreate)
            response.body()
        }
    }

    suspend fun getAllIngredients(): List<IngredientListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllIngredients()
            response.body() ?: emptyList()
        }
    }

    suspend fun getPizza(uid: String): PizzaModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getPizza(uid)
            response.body()
        }
    }

    suspend fun getAllPizzas(): List<PizzaListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllPizzas()
            response.body() ?: emptyList()
        }
    }

    suspend fun getCustomPizza(uid: String): PizzaModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getCustomPizza(uid)
            response.body()
        }
    }

    suspend fun getAllPizzasFromUser(): List<PizzaListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getAllPizzasFromUser()
            response.body() ?: emptyList()
        }
    }

    suspend fun createOrder(orderCreate: OrderCreate): OrderModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).createOrder(orderCreate)
            response.body()
        }
    }

    suspend fun getOrder(uid: String): OrderModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getOrder(uid)
            response.body()
        }
    }

    suspend fun editOrder(uid: String, orderUpdate: OrderUpdate): OrderModel? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).editOrder(uid, orderUpdate)
            response.body()
        }
    }

    suspend fun getCurrentUserOrders(): List<OrderListModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IMakePizzaAPIClient::class.java).getCurrentUserOrders()
            response.body() ?: emptyList()
        }
    }
}