package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "ShoppingCart",
    indices = [
        Index(
            value = ["user_id", "item_id"],
            name = "ix_unq_ShoppingCart_UserID_ItemID",
            unique = true
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CartItemEntity::class,
            parentColumns = ["item_id"],
            childColumns = ["item_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id") val id: Int = 0,

    @ColumnInfo(name = "user_id") val userId: String,

    @ColumnInfo(name = "item_id") val itemId: String
)

data class CartDataModel(
    @Embedded val shoppingCart: CartEntity,

    @Relation(
        parentColumn = "item_id",
        entityColumn = "item_id",
        entity = CartItemEntity::class
    )
    val item: CartItemEntity
)