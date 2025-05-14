package com.example.makepizza_android.domain.usecases.address

import com.example.makepizza_android.data.repository.AddressRepository
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.models.toDomainModel

class GetAddressByID {
    private val repository: AddressRepository = AddressRepository()

    suspend operator fun invoke(addressID: Int): Address {
        return repository.getAddressEntity(addressID).toDomainModel()
    }
}