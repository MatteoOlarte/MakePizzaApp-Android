package com.example.makepizza_android.data.remote.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PizzaListModel(
    @SerializedName("uid") val uid: String,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val desc: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("size") val size: String,
    @SerializedName("image_url") val imageURL: String?
)

data class PizzaModel(
    @SerializedName("uid") val uid: String,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val desc: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("size") val size: String,
    @SerializedName("ingredients") val ingredients: List<IngredientModel>,
    @SerializedName("dough") val dough: IngredientModel,
    @SerializedName("sauce") val sauce: IngredientModel,
    @SerializedName("created_at") val created: Date,
    @SerializedName("updated_at") val updated: Date,
    @SerializedName("image_url") val imageURL: String?
)