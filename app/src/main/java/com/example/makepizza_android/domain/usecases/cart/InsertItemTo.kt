package com.example.makepizza_android.domain.usecases.cart

import com.example.makepizza_android.data.repository.ShoppingCartRepository
import com.example.makepizza_android.domain.models.CartItem
import com.example.makepizza_android.domain.models.toEntityModel

class InsertItemTo {
    private val repository = ShoppingCartRepository()

    suspend operator fun invoke(userID: String, data: CartItem) {
        repository.insertItem(data.toEntityModel(), userID)
    }
}