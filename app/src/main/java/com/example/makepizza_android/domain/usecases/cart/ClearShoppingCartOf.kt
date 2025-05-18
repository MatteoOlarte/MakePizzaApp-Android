package com.example.makepizza_android.domain.usecases.cart

import com.example.makepizza_android.data.repository.ShoppingCartRepository

class ClearShoppingCartOf {
    private val repository = ShoppingCartRepository()

    suspend operator fun invoke(userID: String) = repository.clearCartOf(userID)
}