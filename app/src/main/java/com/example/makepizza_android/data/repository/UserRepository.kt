package com.example.makepizza_android.data.repository

import com.example.makepizza_android.App
import com.example.makepizza_android.data.local.dao.UserDAO
import com.example.makepizza_android.data.local.db.DatabaseProvider
import com.example.makepizza_android.data.local.entity.UserEntity
import com.example.makepizza_android.data.remote.models.UserModel
import com.example.makepizza_android.data.remote.network.MakePizzaAPIService

class UserRepository {
    private val api = MakePizzaAPIService()

    private val dao: UserDAO by lazy {
        DatabaseProvider.getDatabase(App.context).getUserDAO()
    }

    suspend fun getUserEntity(userID: String) = dao.getEntityByID(userID)

    suspend fun getUserModel(userID: String) = dao.getModelByID(userID)

    suspend fun updateUser(user: UserEntity) = dao.update(user)

    suspend fun insertUser(user: UserEntity) = dao.insert(user)

    suspend fun deleteUser(user: UserEntity) = dao.delete(user)

    suspend fun fetchCurrent(): UserModel? = api.getCurrentUser()

    suspend fun getCurrentUser(): UserModel? {
        val response = api.getCurrentUser()
        return response
    }
}