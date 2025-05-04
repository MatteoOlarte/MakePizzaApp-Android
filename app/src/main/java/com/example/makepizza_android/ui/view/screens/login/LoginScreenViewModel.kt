package com.example.makepizza_android.ui.view.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.auth.FirebaseEmailAuthentication
import com.example.makepizza_android.domain.auth.models.FirebaseUserLogin
import com.example.makepizza_android.domain.usecases.LoginUserUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _uiState = MutableStateFlow<LoginScreenState>(LoginScreenState.Loading)
    val uiState: StateFlow<LoginScreenState> = _uiState.asStateFlow()

    fun handleUserNameChange(value: String) {
        _username.value = value
    }

    fun handlePasswordChange(value: String) {
        _password.value = value
    }

    fun handleOnDone() {
        _uiState.value = LoginScreenState.Loading

        val usernameValue = _username.value
        val passwordValue = _password.value

        if (usernameValue == null || usernameValue.isEmpty()) {
            _uiState.value = LoginScreenState.MissingEmail
            return
        }

        if (passwordValue == null || passwordValue.isEmpty()) {
            _uiState.value = LoginScreenState.MissingPassword
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            launchLoginAttemptWithEmail(usernameValue, passwordValue)
        }
    }

    private fun launchLoginAttemptWithEmail(usernameValue: String, passwordValue: String) {
        val data = FirebaseUserLogin(username = usernameValue, password = passwordValue)
        val auth = FirebaseEmailAuthentication()

        LoginUserUseCase().invoke(data, auth).also {
            it.addOnSuccessListener { _uiState.value = LoginScreenState.Success }
            it.addOnFailureListener { _uiState.value = getErrorStateOf(it) }
        }
    }

    private fun getErrorStateOf(ex: Exception) = when (ex) {
        is FirebaseAuthInvalidCredentialsException -> LoginScreenState.InvalidCredentials
        else -> LoginScreenState.Error("${ex.message}")
    }
}