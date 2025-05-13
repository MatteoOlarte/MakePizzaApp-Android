package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.AddressDAO
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.AddressEntity

class AddressRepository {
    private val dao: AddressDAO by lazy {
        DatabaseProvider.getDatabase(App.context).getAddressDAO()
    }

    suspend fun getAddressEntity(addressID: Int) = dao.getEntityByID(addressID)

    suspend fun getAddressModel(addressID: Int) = dao.getModelByID(addressID)

    suspend fun updateAddress(address: AddressEntity) = dao.update(address)

    suspend fun insertAddress(address: AddressEntity) = dao.insert(address)

    suspend fun deleteAddress(address: AddressEntity) = dao.delete(address)
}