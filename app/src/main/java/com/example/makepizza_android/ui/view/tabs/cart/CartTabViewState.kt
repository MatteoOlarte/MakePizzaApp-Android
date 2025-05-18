package com.example.makepizza_android.ui.view.tabs.cart

sealed class CartTabViewState {
    object Loading: CartTabViewState()

    object Success: CartTabViewState()

    object Error: CartTabViewState()
}