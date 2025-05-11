package com.example.makepizza_android.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.makepizza_android.data.local.dao.AddressDAO
import com.example.makepizza_android.data.local.entity.AddressEntity


@Database(
    entities = [AddressEntity::class],
    version = 1
)
abstract class ApplicationDataBase: RoomDatabase() {
    abstract fun getAddressDAO(): AddressDAO
}