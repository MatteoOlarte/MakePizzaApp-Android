package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.CartItemDAO
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.CartItemEntity

class CartItemRepository {
    private val dao: CartItemDAO by lazy {
        DatabaseProvider.getDatabase(App.context).getCartItemDAO()
    }

    suspend fun insert(data: CartItemEntity) = dao.insert(data)
}