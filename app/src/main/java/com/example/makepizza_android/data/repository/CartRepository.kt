package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.CartDAO
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.CartEntity

class CartRepository {
    private val dao: CartDAO by lazy {
        DatabaseProvider.getDatabase(App.context).getCartDAO()
    }

    suspend fun insert(data: CartEntity) = dao.insert(data)

    suspend fun delete(cartID: Int) = dao.delete(cartID)

    suspend fun deleteAll(userID: String) = dao.deleteAll(userID)
}