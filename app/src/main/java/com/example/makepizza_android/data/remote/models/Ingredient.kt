package com.example.makepizza_android.data.remote.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class IngredientListModel(
    val uid: String,
    val name: String,
    val desc: String?,
    val price: Float,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("image_url") val imageURL: String?
)

data class IngredientModel(
    @SerializedName("uid") val uid: String,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val desc: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("ingredient_type") val type: String,
    @SerializedName("created_at") val created: Date,
    @SerializedName("updated_at") val updated: Date,
    @SerializedName("image_url") val imageURL: String?
)