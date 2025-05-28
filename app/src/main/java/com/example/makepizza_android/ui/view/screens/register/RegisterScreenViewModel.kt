package com.example.makepizza_android.ui.view.screens.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.auth.models.FirebaseUserCreate
import com.example.makepizza_android.domain.usecases.user.CreateUserWithEmailAndPPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password1 = MutableLiveData<String>()
    val password1: LiveData<String> = _password1

    private val _password2 = MutableLiveData<String>()
    val password2: LiveData<String> = _password2

    private val _uiState = MutableStateFlow<RegisterScreenViewState>(RegisterScreenViewState.IsEmpty)
    val uiState = _uiState.asStateFlow()

    private val createUserUserCase = CreateUserWithEmailAndPPasswordUseCase()

    fun handleNameChange(value: String) {
        _name.value = value
    }

    fun handleEmailChange(value: String) {
        _email.value = value
    }

    fun handlePassword1Change(value: String) {
        _password1.value = value
    }

    fun handlePassword2Change(value: String) {
        _password2.value = value
    }

    fun handleOnDone() {
        viewModelScope.launch { handleOnDoneUImpl() }
    }

    private suspend fun handleOnDoneUImpl() {
        _uiState.value = RegisterScreenViewState.Loading

        val nameValue = _name.value
        val emailValue = _email.value
        val password1Value = _password1.value
        val password2Value = _password2.value

        if (nameValue.isNullOrEmpty()) {
            _uiState.value = RegisterScreenViewState.OnError(err = "UserNameIsEmpty")
            return
        }

        if (emailValue.isNullOrEmpty()) {
            _uiState.value = RegisterScreenViewState.OnError(err = "EmailIsEmpty")
            return
        }

        if (password1Value.isNullOrEmpty()) {
            _uiState.value = RegisterScreenViewState.OnError(err = "Password1IsEmpty")
            return
        }

        if (password2Value.isNullOrEmpty()) {
            _uiState.value = RegisterScreenViewState.OnError(err = "Password2IsEmpty")
            return
        }

        if (password1Value != password2Value) {
            _uiState.value = RegisterScreenViewState.OnError(err = "PasswordsMismatch")
            return
        }

        try {
            createUserWithEmailAndPassword(nameValue, emailValue, password1Value)
            _uiState.value = RegisterScreenViewState.Success
        } catch (_: Exception) {
            _uiState.value = RegisterScreenViewState.OnError("NetworkError")
        }
    }

    private suspend fun createUserWithEmailAndPassword(name: String, email: String, pass: String) {
        val data = FirebaseUserCreate(
            displayName = name,
            email = email,
            password = pass,
            photoURL = null,
            phoneNumber = null
        )
        createUserUserCase(data)
    }
}