package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makepizza_android.data.local.entity.CartItemEntity

@Dao
interface CartItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CartItemEntity)

    @Query("SELECT * FROM CartItem WHERE item_id = :itemId")
    suspend fun getById(itemId: String): CartItemEntity?
}