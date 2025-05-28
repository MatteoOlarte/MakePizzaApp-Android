package com.example.makepizza_android.domain.usecases.user

import com.example.makepizza_android.data.remote.models.UserCreate
import com.example.makepizza_android.data.repository.UserRepository
import com.example.makepizza_android.domain.auth.FirebaseEmailAuthentication
import com.example.makepizza_android.domain.auth.models.FirebaseUserCreate
import com.example.makepizza_android.domain.auth.models.FirebaseUserLogin
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.models.toDomainModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class CreateUserWithEmailAndPPasswordUseCase {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val emailAuth = FirebaseEmailAuthentication()
    private val repository = UserRepository()

    suspend operator fun invoke(data: FirebaseUserCreate): User? {
        firebaseAuth.signOut()
        emailAuth.createUser(data).await()
        emailAuth.loginUser(FirebaseUserLogin(data.email, data.password)).await()
        return createUserData(data)
    }

    private suspend fun createUserData(data: FirebaseUserCreate): User? {
        val user = UserCreate(data.displayName!!, data.email, data.password)
        return repository.createUserRemote(user)?.toDomainModel()
    }
}