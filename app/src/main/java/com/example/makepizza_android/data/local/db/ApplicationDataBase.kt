package com.example.makepizza_android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.makepizza_android.data.local.dao.AddressDAO
import com.example.makepizza_android.data.local.dao.ShoppingCartDao
import com.example.makepizza_android.data.local.dao.UserDAO
import com.example.makepizza_android.data.local.entity.AddressEntity
import com.example.makepizza_android.data.local.entity.CartItemEntity
import com.example.makepizza_android.data.local.entity.ShoppingCart
import com.example.makepizza_android.data.local.entity.UserEntity
import com.example.makepizza_android.data.local.types.DateConverter


@Database(
    entities = [
        UserEntity::class,
        AddressEntity::class,
        ShoppingCart::class,
        CartItemEntity::class
    ],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class ApplicationDataBase : RoomDatabase() {
    abstract fun getAddressDAO(): AddressDAO

    abstract fun getUserDAO(): UserDAO

    abstract fun getShoppingCartDAO(): ShoppingCartDao
}