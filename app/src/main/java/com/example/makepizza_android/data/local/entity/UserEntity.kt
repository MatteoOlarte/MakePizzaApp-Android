package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(
    tableName = "users",
    foreignKeys = [
        ForeignKey(
            entity = AddressEntity::class,
            parentColumns = ["address_id"],
            childColumns = ["active_address"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id") val id: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "is_admin") val isAdmin: Boolean,

    @ColumnInfo(name = "created_at") val createdAt: Date,

    @ColumnInfo(name = "updated_at") val updatedAt: Date,

    @ColumnInfo(name = "active_address") val activeAddressID: Int?,

    @ColumnInfo(name = "cached_at") val cachedAt: Date = Date()
)

data class UserDataModel(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val addresses: List<AddressEntity>,

    @Relation(
        parentColumn = "active_address",
        entityColumn = "address_id",
    )
    val address: AddressEntity?
)