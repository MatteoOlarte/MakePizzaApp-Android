package com.example.makepizza_android.ui.view.tabs.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.models.UserModel
import com.example.makepizza_android.data.repository.UserRepository
import com.example.makepizza_android.domain.usecases.SignOutUseCase
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

    init {
        viewModelScope.launch { launchTasks() }
    }

    fun handleUserLogout() {
        viewModelScope.launch { logoutUser() }
    }

    private suspend fun launchTasks() {
        try {
            fetchCurrentUserModel()
        } catch (ex: Exception) {
            _uiState.value = AccountTabState.Error(ex.message!!)
        }
    }

    private suspend fun logoutUser() {
        SignOutUseCase().invoke().fold(
            onSuccess = {
                _uiState.value = AccountTabState.Success(hasCurrentUser = false)
            },
            onFailure = { error ->
                _uiState.value = AccountTabState.Error(error.message ?: "Failed to sign out")
            }
        )
    }

    private suspend fun fetchCurrentUserModel() {
        _uiState.value = AccountTabState.Loading

        val user: UserModel? = userRepository.getCurrentUser()
        _currentUser.postValue(user)
        _uiState.value = AccountTabState.Success(hasCurrentUser = user != null)
    }
}
