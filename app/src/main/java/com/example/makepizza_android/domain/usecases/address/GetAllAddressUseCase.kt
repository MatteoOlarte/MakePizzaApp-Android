package com.example.makepizza_android.domain.usecases.address

import com.example.makepizza_android.data.repository.AddressRepository
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.models.toDomainModel

class GetAllAddressUseCase {
    private val repository: AddressRepository = AddressRepository()

    suspend operator fun invoke(ownerID: String): List<Address> {
        val orders = repository.getAllAddressesFromUser(ownerID)
        return orders.map { it.toDomainModel() }
    }
}