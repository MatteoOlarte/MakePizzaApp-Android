package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.makepizza_android.data.local.entity.CartItemEntity
import com.example.makepizza_android.data.local.entity.ShoppingCart

@Dao
interface ShoppingCartDao  {
    @Query("delete from ShoppingCart where user_id = :userID")
    suspend fun clear(userID: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: ShoppingCart)
}