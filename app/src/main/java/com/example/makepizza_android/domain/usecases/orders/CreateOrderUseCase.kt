package com.example.makepizza_android.domain.usecases.orders

import com.example.makepizza_android.data.remote.models.OrderCreate
import com.example.makepizza_android.data.repository.OrderRepository
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.models.Cart
import com.example.makepizza_android.domain.models.Order
import com.example.makepizza_android.domain.models.toDomainModel

class CreateOrderUseCase {
    val repository: OrderRepository = OrderRepository()

    suspend operator fun invoke(address: Address, items: List<Cart>): Order? {
        val data = OrderCreate(address.addressValue, 0.0f, items.map { it.item.id })
        return repository.createOrder(data)?.toDomainModel()
    }
}