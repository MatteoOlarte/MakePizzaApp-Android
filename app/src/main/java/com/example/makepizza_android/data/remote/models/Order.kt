package com.example.makepizza_android.data.remote.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class OrderModel (
    @SerializedName("uid") val uid: String,
    @SerializedName("delivery_address") val deliveryAddress: String,
    @SerializedName("tip_amount") val tipAmount: Float,
    @SerializedName("delivery_fee") val deliveryFee: Float,
    @SerializedName("total_price") val totalPrice: Float,
    @SerializedName("status") val status: String,
    @SerializedName("pizzas") val pizzas: List<PizzaListModel>,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)

data class OrderCreate(
    @SerializedName("delivery_address") val deliveryAddress: String,
    @SerializedName("tip_amount") val tipAmount: Float,
    @SerializedName("pizzas_ids") val pizzaIds: List<String>
)

data class OrderUpdate(
    @SerializedName("delivery_address") val deliveryAddress: String,
    @SerializedName("tip_amount") val tipAmount: Float
)
