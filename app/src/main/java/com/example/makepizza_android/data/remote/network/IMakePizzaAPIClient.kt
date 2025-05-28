package com.example.makepizza_android.data.remote.network

import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.models.OrderCreate
import com.example.makepizza_android.data.remote.models.OrderListModel
import com.example.makepizza_android.data.remote.models.OrderModel
import com.example.makepizza_android.data.remote.models.OrderUpdate
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.data.remote.models.PizzaModel
import com.example.makepizza_android.data.remote.models.UserCreate
import com.example.makepizza_android.data.remote.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface IMakePizzaAPIClient {
    @GET("/users/current")
    suspend fun getCurrentUser(): Response<UserModel?>

    @POST("/accounts/authenticate/sign-up")
    suspend fun signUp(@Body user: UserCreate): Response<UserModel?>

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

    @POST("/orders")
    suspend fun createOrder(@Body orderCreate: OrderCreate): Response<OrderModel>

    @GET("/orders")
    suspend fun getOrder(@Query("order_uid") uid: String): Response<OrderModel>

    @PUT("/orders")
    suspend fun editOrder(@Query("order_uid") uid: String, @Body orderUpdate: OrderUpdate): Response<OrderModel>

    @GET("/orders/fetch-all/from-current-user")
    suspend fun getCurrentUserOrders(): Response<List<OrderListModel>>
}