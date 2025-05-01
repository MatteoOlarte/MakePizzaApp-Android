package com.example.makepizza_android.ui.view.screens.login

sealed class LoginScreenState {
    object Loading: LoginScreenState()
    object Success: LoginScreenState()
    object InvalidCredentials: LoginScreenState()
    object MissingEmail: LoginScreenState()
    object MissingPassword: LoginScreenState()
    data class Error(val message: String): LoginScreenState();
}