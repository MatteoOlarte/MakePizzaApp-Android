package com.example.makepizza_android.domain.usecases

import com.example.makepizza_android.core.MakePizzaAPI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignOutUseCase {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend operator fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.signOut()
            MakePizzaAPI.clearCache()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}