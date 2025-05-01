package com.example.makepizza_android.ui.view.tabs.customize

sealed class CustomizeTabState {
    object Loading : CustomizeTabState()
    data class Success(val hasCurrentUser: Boolean) : CustomizeTabState()
    data class Error(val message: String) : CustomizeTabState()
}