package com.example.makepizza_android.domain.usecases.cart

import com.example.makepizza_android.data.repository.CartRepository

class DeleteItem {
    private val cartRepository = CartRepository()

    suspend operator fun invoke(cartID: Int) {
        cartRepository.delete(cartID)
    }
}