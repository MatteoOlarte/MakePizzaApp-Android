package com.example.makepizza_android.domain.usecases.cart

import com.example.makepizza_android.data.local.entity.CartEntity
import com.example.makepizza_android.data.repository.CartItemRepository
import com.example.makepizza_android.data.repository.CartRepository
import com.example.makepizza_android.domain.models.CartItem
import com.example.makepizza_android.domain.models.toEntityModel

class InsertItem {
    private val cartRepository = CartRepository()

    private val cartItemRepository = CartItemRepository()

    suspend operator fun invoke(userID: String, data: CartItem) {
        cartItemRepository.insert(data.toEntityModel())
        cartRepository.insert(CartEntity(userId = userID, itemId = data.id))
    }
}