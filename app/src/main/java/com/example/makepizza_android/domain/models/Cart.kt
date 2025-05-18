package com.example.makepizza_android.domain.models

import com.example.makepizza_android.data.local.entity.CartDataModel
import com.example.makepizza_android.data.local.entity.CartEntity

data class Cart(
    val id: Int = 0,
    val item: CartItem,
)

fun CartDataModel.toDomainModel() = Cart(
    id = this.shoppingCart.id,
    item = this.item.toDomainModel()
)

fun Cart.toEntityModel(userID: String) = CartEntity(
    id = this.id,
    itemId = this.item.id,
    userId = userID
)