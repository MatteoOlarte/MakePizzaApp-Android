package com.example.makepizza_android.domain.usecases.orders

import com.example.makepizza_android.data.repository.OrderRepository
import com.example.makepizza_android.domain.models.OrderList
import com.example.makepizza_android.domain.models.toDomainModel

class GetAllCurrentUserOrdersUseCase {
    val repository: OrderRepository = OrderRepository()

    suspend operator fun invoke(): List<OrderList> {
        val orders = repository.getAllFromCurrentUser()
        return orders.map { it.toDomainModel() }
    }
}