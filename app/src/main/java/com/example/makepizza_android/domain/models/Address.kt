package com.example.makepizza_android.domain.models

import com.example.makepizza_android.data.local.entity.AddressEntity

data class Address(
    val id: Int,
    val addressName: String,
    val addressValue: String,
    val ownerID: String
)

fun AddressEntity.toDomainModel() = Address(id, addressName, addressValue, ownerID)

fun Address.toDatabaseModel() = AddressEntity(id, addressName, addressValue, ownerID)
