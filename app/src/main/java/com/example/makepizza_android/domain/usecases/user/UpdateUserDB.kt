package com.example.makepizza_android.domain.usecases.user

import com.example.makepizza_android.data.repository.UserRepository
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.models.toEntityModel

class UpdateUserDB {
    val repository: UserRepository = UserRepository()

    suspend operator fun invoke(data: User) = repository.updateUser(data.toEntityModel())
}