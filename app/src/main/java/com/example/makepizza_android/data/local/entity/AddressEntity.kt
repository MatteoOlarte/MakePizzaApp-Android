package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Addresses",
    indices = [
        Index(value = ["user_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id") val id: Int = 0,

    @ColumnInfo(name = "address_name") val addressName: String,

    @ColumnInfo(name = "address_value") val addressValue: String,

    @ColumnInfo(name = "user_id") val ownerID: String
)

data class AddressDataModel(
    @Embedded val address: AddressEntity
)