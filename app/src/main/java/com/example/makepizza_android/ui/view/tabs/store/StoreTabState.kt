package com.example.makepizza_android.ui.view.tabs.store

sealed class StoreTabState {
    object Loading: StoreTabState()
    object Success: StoreTabState()
    data class Error(val message: String): StoreTabState()
}