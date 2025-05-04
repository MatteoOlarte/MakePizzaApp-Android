package com.example.makepizza_android.data.repository

import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.models.PizzaModel
import com.example.makepizza_android.data.network.MakePizzaAPIService

class PizzaRepository {
    private val api = MakePizzaAPIService()

    suspend fun getPizza(uid: String): PizzaModel? {
        val response = api.getPizza(uid)
        return response
    }

    suspend fun getAllPizzas(): List<PizzaListModel> {
        val response = api.getAllPizzas()
        return response
    }

    suspend fun getAllPizzasFromUser(): List<PizzaListModel> {
        val response = api.getAllPizzasFromUser()
        return response
    }
}