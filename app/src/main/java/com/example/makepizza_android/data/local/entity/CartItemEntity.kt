package com.example.makepizza_android.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
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

data class CartItemDataModel(
    @Embedded val item: CartItemEntity
)