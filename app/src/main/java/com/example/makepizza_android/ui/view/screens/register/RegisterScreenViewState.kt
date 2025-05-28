package com.example.makepizza_android.ui.view.screens.register

sealed class RegisterScreenViewState {
    object IsEmpty: RegisterScreenViewState()
    object Loading: RegisterScreenViewState()
    object Success: RegisterScreenViewState()
    data class OnError(val err: String): RegisterScreenViewState()
}