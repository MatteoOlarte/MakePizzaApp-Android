package com.example.makepizza_android.domain.usecases.cart

import com.example.makepizza_android.data.repository.CartRepository

class ClearCartUseCase {
    private val cartRepository = CartRepository()

    suspend operator fun invoke(userID: String) {
        cartRepository.deleteAll(userID)
    }
}