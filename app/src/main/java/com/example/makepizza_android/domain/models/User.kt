package com.example.makepizza_android.domain.models

import com.example.makepizza_android.data.local.entity.UserDataModel
import com.example.makepizza_android.data.local.entity.UserEntity
import com.example.makepizza_android.data.remote.models.UserModel
import java.util.Date

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val isAdmin: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
    val address: Address?,
    val addresses: List<Address> = emptyList()
)

fun UserDataModel.toDomainModel() = User(
    uid = this.user.id,
    name = this.user.name,
    email = this.user.email,
    isAdmin = this.user.isAdmin,
    createdAt = this.user.createdAt,
    updatedAt = this.user.updatedAt,
    address = this.address?.toDomainModel(),
    addresses = this.addresses.map { it.toDomainModel() }
)

fun UserModel.toDomainModel() = User(
    uid = this.uid,
    name = this.name,
    email = this.email,
    isAdmin = this.isAdmin,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    address = null,
    addresses = emptyList()
)

fun User.toEntityModel() = UserEntity(
    id = this.uid,
    name = this.name,
    email = this.email,
    isAdmin = this.isAdmin,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    activeAddressID = this.address?.id
)
