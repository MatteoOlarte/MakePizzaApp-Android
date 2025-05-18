package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makepizza_android.data.local.entity.CartEntity

@Dao
interface CartDAO  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CartEntity)

    @Query("DELETE FROM ShoppingCart WHERE cart_id = :cartID")
    suspend fun delete(cartID: Int)

    @Query("DELETE FROM ShoppingCart WHERE user_id = :userID")
    suspend fun deleteAll(userID: String)
}