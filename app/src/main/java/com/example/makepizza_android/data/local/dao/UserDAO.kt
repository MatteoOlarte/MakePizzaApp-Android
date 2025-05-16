package com.example.makepizza_android.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.makepizza_android.data.local.entity.UserDataModel
import com.example.makepizza_android.data.local.entity.UserEntity

@Dao
interface UserDAO {
    @Transaction
    @Query("select * from Users where user_id = :userID")
    suspend fun getEntityByID(userID: String): UserEntity?

    @Transaction
    @Query("select * from Users where user_id = :userID")
    suspend fun getModelByID(userID: String): UserDataModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}
