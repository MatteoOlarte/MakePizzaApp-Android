package com.example.makepizza_android.ui.view.tabs.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.remote.models.UserModel
import com.example.makepizza_android.data.repository.UserRepository
import com.example.makepizza_android.domain.usecases.SignOutUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountTabViewModel: ViewModel() {
    private val _currentUser = MutableLiveData<UserModel?>()
    val currentUser: LiveData<UserModel?> = _currentUser

    private val _uiState = MutableStateFlow<AccountTabState>(AccountTabState.Loading)
    val uiState: StateFlow<AccountTabState> = _uiState.asStateFlow()

    val userRepository = UserRepository()

    fun fetchAllData() {
        viewModelScope.launch { fetchCurrentUser() }
    }

    fun handleUserLogout() {
        viewModelScope.launch { logoutCurrentUser() }
    }

    private suspend fun logoutCurrentUser() {
        SignOutUseCase().invoke().fold(
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

        val auth = Firebase.auth
        auth.currentUser?.getIdToken(true).also {
            it?.addOnSuccessListener { Log.d("AccountTabViewModel", "${it.token}") }
        }

        try {
            val user: UserModel? = userRepository.getCurrentUser()
            _currentUser.value = user
            _uiState.value = AccountTabState.Success(hasCurrentUser = user != null)
        } catch (ex: Exception) {
            _uiState.value = AccountTabState.Error(ex.message!!)
        }
    }
}
