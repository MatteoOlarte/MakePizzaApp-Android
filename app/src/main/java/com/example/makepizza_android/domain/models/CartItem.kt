package com.example.makepizza_android.domain.models

import com.example.makepizza_android.data.local.entity.CartItemEntity
import com.example.makepizza_android.data.remote.models.PizzaModel

data class CartItem (
    val id: String = "",
    val name: String,
    val desc: String?,
    val price: Float,
    val size: String
)

fun CartItemEntity.toDomainModel() = CartItem(
    id = this.id,
    name = this.name,
    desc = this.desc,
    price = this.price,
    size = this.size
)

fun PizzaModel.toDomainModel() = CartItem(
    id = this.uid,
    name = this.name,
    desc = this.desc,
    price = this.price,
    size = this.size
)

fun CartItem.toEntityModel() = CartItemEntity(
    id = this.id,
    name = this.name,
    desc = this.desc,
    price = this.price,
    size = this.size
)
