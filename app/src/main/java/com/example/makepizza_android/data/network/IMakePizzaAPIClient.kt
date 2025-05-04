package com.example.makepizza_android.data.network

import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.models.PizzaModel
import com.example.makepizza_android.data.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IMakePizzaAPIClient {
    @GET("/users/current")
    suspend fun getCurrentUser(): Response<UserModel?>

    @GET("/ingredients/fetch-all")
    suspend fun getAllIngredients(): Response<List<IngredientListModel>>

    @GET("/pizzas")
    suspend fun getPizza(@Query("pizza_uid") uid: String): Response<PizzaModel>

    @GET("/pizzas/fetch-all")
    suspend fun getAllPizzas(): Response<List<PizzaListModel>>

    @GET("/pizzas/user-defined")
    suspend fun getCustomPizza(@Query("pizza_uid") uid: String): Response<PizzaModel>

    @GET("/pizzas/user-defined/fetch-all")
    suspend fun getAllPizzasFromUser(): Response<List<PizzaListModel>>
}