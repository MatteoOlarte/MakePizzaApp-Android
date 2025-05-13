package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.makepizza_android.data.local.entity.AddressDataModel
import com.example.makepizza_android.data.local.entity.AddressEntity

@Dao
interface AddressDAO {
    @Transaction
    @Query("select * from shipping_addresses where address_id = :addressID")
    suspend fun getEntityByID(addressID: Int): AddressEntity

    @Transaction
    @Query("select * from shipping_addresses where address_id = :addressID")
    suspend fun getModelByID(addressID: Int): AddressDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: AddressEntity)

    @Update
    suspend fun update(address: AddressEntity)

    @Delete
    suspend fun delete(address: AddressEntity)
}