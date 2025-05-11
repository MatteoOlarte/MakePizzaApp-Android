package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.AddressDAO
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.AddressEntity

class AddressRepository {
    private val addressDao: AddressDAO by lazy {
        DatabaseProvider.getDatabase(App.context).getAddressDAO()
    }

    suspend fun saveAddress(address: AddressEntity) {
        addressDao.insertNewAddress(address)
    }

    suspend fun getAllAddressesFromUser(ownerId: String): List<AddressEntity> {
        return addressDao.getAllAddressesFromUser(ownerId)
    }
}