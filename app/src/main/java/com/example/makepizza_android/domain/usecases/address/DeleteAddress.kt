package com.example.makepizza_android.domain.usecases.address

import com.example.makepizza_android.data.repository.AddressRepository
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.models.toDatabaseModel

class DeleteAddress {
    private val repository: AddressRepository = AddressRepository()

    suspend operator fun invoke(data: Address) = repository.deleteAddress(data.toDatabaseModel())
}