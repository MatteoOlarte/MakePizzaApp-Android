package com.example.makepizza_android.ui.view.tabs.account

sealed class AccountTabState {
    object Loading: AccountTabState()
    data class Success(val hasCurrentUser: Boolean): AccountTabState()
    data class Error(val message: String): AccountTabState()
}