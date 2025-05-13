package com.example.makepizza_android.ui.view.tabs.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.usecases.SignOutUseCase
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountTabViewModel: ViewModel() {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _uiState = MutableStateFlow<AccountTabState>(AccountTabState.Loading)
    val uiState: StateFlow<AccountTabState> = _uiState.asStateFlow()

    private val currentUserUseCase = CurrentUser()

    private val signOutUseCase = SignOutUseCase()

    fun fetchCurrentUserData() {
        viewModelScope.launch { fetchCurrentUser() }
    }

    fun handleUserLogout() {
        viewModelScope.launch { logoutCurrentUser() }
    }

    private suspend fun logoutCurrentUser() {
        signOutUseCase().fold(
            onSuccess = {
                _uiState.value = AccountTabState.Success(hasCurrentUser = false)
            },
            onFailure = { error ->
                _uiState.value = AccountTabState.Error(error.message ?: "Failed to sign out")
            }
        )
    }

    private suspend fun fetchCurrentUser() {
        _uiState.value = AccountTabState.Loading

        try {
            val user = currentUserUseCase()
            Log.d("AccountTabViewModel", "$user")
            _currentUser.value = user
            _uiState.value = AccountTabState.Success(hasCurrentUser = user != null)
        } catch (ex: Exception) {
            _uiState.value = AccountTabState.Error(ex.message!!)
        }

        val auth = Firebase.auth
        auth.currentUser?.getIdToken(true).also {
            it?.addOnSuccessListener { Log.d("AccountTabViewModel", "${it.token}") }
            it?.addOnSuccessListener { Log.d("AccountTabViewModel", "${_uiState.value}") }
        }
    }
}
