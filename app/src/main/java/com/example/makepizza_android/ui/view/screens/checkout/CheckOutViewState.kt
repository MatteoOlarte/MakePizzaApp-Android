package com.example.makepizza_android.ui.view.screens.checkout

sealed class CheckOutViewState {
    object Success: CheckOutViewState()
    object Loading: CheckOutViewState()
    object Updated: CheckOutViewState()
    data class OnError(val err: String): CheckOutViewState()
}