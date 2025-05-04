package com.example.makepizza_android.domain.usecases

import com.example.makepizza_android.core.MakePizzaAPI
import com.example.makepizza_android.domain.auth.IFirebaseAuth
import com.example.makepizza_android.domain.auth.models.FirebaseUserLogin
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginUserUseCase {
    operator fun invoke(data: FirebaseUserLogin, login: IFirebaseAuth): Task<AuthResult> {
        MakePizzaAPI.clearCache()
        return login.loginUser(data)
    }
}