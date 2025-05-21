package com.example.makepizza_android.ui.view.screens.orders

sealed class OrderScreenViewState {
    object Loading : OrderScreenViewState()
    object Success : OrderScreenViewState()
    data class OnError(val err: String) : OrderScreenViewState()
}