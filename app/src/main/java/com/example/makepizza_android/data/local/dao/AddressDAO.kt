package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makepizza_android.data.local.entity.AddressEntity


@Dao
interface AddressDAO {
    @Query("select * from UsersAddress where owner_id == :ownerId")
    suspend fun getAllAddressesFromUser(ownerId: String): List<AddressEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewAddress(data: AddressEntity)
}