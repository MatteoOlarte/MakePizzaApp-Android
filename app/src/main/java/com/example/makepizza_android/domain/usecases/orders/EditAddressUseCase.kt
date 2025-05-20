package com.example.makepizza_android.domain.usecases.orders

import com.example.makepizza_android.data.remote.models.OrderUpdate
import com.example.makepizza_android.data.repository.OrderRepository

class EditAddressUseCase {
    val repository: OrderRepository = OrderRepository()

    suspend operator fun invoke(orderID: String, addressValue: String, tipAmount: Float) {
        val data = OrderUpdate(addressValue, tipAmount)
        repository.editOrder(orderID, data)
    }
}