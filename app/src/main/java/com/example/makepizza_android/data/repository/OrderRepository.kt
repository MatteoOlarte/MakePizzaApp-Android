package com.example.makepizza_android.data.repository

import com.example.makepizza_android.data.remote.models.OrderCreate
import com.example.makepizza_android.data.remote.models.OrderListModel
import com.example.makepizza_android.data.remote.models.OrderModel
import com.example.makepizza_android.data.remote.models.OrderUpdate
import com.example.makepizza_android.data.remote.network.MakePizzaAPIService

class OrderRepository {
    private val api = MakePizzaAPIService()

    suspend fun createOrder(orderCreate: OrderCreate): OrderModel? {
        return api.createOrder(orderCreate)
    }

    suspend fun getOrder(uid: String): OrderModel? {
        return api.getOrder(uid)
    }

    suspend fun editOrder(uid: String, orderUpdate: OrderUpdate): OrderModel? {
        return api.editOrder(uid, orderUpdate)
    }

    suspend fun getAllFromCurrentUser(): List<OrderListModel> {
        return api.getCurrentUserOrders()
    }
}