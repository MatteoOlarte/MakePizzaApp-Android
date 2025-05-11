package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "UsersAddress",
    indices = [Index(value = ["owner_id"])]
)
data class AddressEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id") val id: Int = 0,

    @ColumnInfo(name = "address_name") val addressName: String,

    @ColumnInfo(name = "address_value") val addressValue: String,

    @ColumnInfo(name = "owner_id") val ownerID: String
)