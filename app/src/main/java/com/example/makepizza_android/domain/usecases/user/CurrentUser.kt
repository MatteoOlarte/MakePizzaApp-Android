package com.example.makepizza_android.domain.usecases.user

import com.example.makepizza_android.data.remote.models.UserModel
import com.example.makepizza_android.data.repository.UserRepository
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.models.toDomainModel
import com.example.makepizza_android.domain.models.toEntityModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CurrentUser {
    private val repository: UserRepository = UserRepository()

    suspend operator fun invoke(): User? {
        val userID = Firebase.auth.currentUser?.uid
        if (userID == null) return null

        val userDB = repository.getUserModel(userID)

        if (userDB == null) {
            val userAPI = repository.fetchCurrent()
            return handleDataCashing(userAPI)
        }
        return userDB.toDomainModel()
    }

    private suspend fun handleDataCashing(data: UserModel?): User? {
        if (data == null) return null

        val user = data.toDomainModel()
        repository.insertUser(user.toEntityModel())
        return user
    }
}