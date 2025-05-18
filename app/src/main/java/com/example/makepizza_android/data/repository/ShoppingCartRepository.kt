package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.ShoppingCartDao
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.CartItemEntity
import com.example.makepizza_android.data.local.entity.ShoppingCart


class ShoppingCartRepository {
    private val dao: ShoppingCartDao by lazy {
        DatabaseProvider.getDatabase(App.context).getShoppingCartDAO()
    }

    suspend fun clearCartOf(userID: String) = dao.clear(userID)

    suspend fun insertItem(item: CartItemEntity, userId: String) {
        val ref = ShoppingCart(userId = userId, itemId = item.id)
        dao.insertItem(item)
        dao.insertCrossRef(ref)
    }
}