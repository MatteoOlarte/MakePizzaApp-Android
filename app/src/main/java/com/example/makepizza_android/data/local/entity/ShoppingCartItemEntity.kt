package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "CartItem"
)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "item_id") val id: String = "",

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "desc") val desc: String?,

    @ColumnInfo(name = "price") val price: Float,

    @ColumnInfo(name = "size") val size: String,

    @ColumnInfo(name = "cached_at") val cachedAt: Date = Date()
)

@Entity(
    tableName = "ShoppingCart",
    primaryKeys = ["user_id", "item_id"],
    indices = [
        Index("user_id", name = "ix_ShoppingCart_user_id"),
        Index("item_id", name = "ix_ShoppingCart_item_id")
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
data class ShoppingCart(
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "item_id") val itemId: String
)