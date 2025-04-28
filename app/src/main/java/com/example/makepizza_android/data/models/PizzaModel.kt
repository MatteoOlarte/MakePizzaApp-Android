package com.example.makepizza_android.data.models

import com.google.gson.annotations.SerializedName

data class PizzaListModel(
    @SerializedName("uid") val uid: String,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val desc: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("size") val size: String
)
