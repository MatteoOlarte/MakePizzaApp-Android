package com.example.makepizza_android.data.models

import com.google.gson.annotations.SerializedName

data class IngredientListModel(
    val uid: String,
    val name: String,
    val desc: String?,
    val price: Float,
    @SerializedName("is_available") val isAvailable: Boolean,
)