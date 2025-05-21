package com.example.makepizza_android.domain.models

import com.example.makepizza_android.data.remote.models.OrderListModel
import com.example.makepizza_android.data.remote.models.OrderModel
import com.example.makepizza_android.data.remote.models.PizzaListModel
import java.util.Date

data class Order(
    val uid: String,
    val deliveryAddress: String,
    val tipAmount: Float,
    val deliveryFee: Float,
    val totalPrice: Float,
    val status: String,
    val pizzas: List<PizzaListModel>,
    val createdAt: Date,
    val updatedAt: Date
)

data class OrderList(
    val deliveryAddress: String,
    val tipAmount: Float,
    val uid: String,
    val deliveryFee: Float,
    val totalPrice: Float,
    val status: String
)

fun OrderModel.toDomainModel() = Order(
    uid = this.uid,
    deliveryAddress = this.deliveryAddress,
    tipAmount = this.tipAmount,
    deliveryFee = this.deliveryFee,
    totalPrice = this.totalPrice,
    status = this.status,
    pizzas = this.pizzas,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun OrderListModel.toDomainModel() = OrderList(
    uid = this.uid,
    deliveryAddress = this.deliveryAddress,
    tipAmount = this.tipAmount,
    deliveryFee = this.deliveryFee,
    totalPrice = this.totalPrice,
    status = this.status
)