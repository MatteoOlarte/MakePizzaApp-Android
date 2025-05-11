package com.example.makepizza_android.ui.view.screens.address.create

sealed class AddressCreateViewState {
    object Nothing: AddressCreateViewState();
    object Loading: AddressCreateViewState();
    object Success: AddressCreateViewState();
}