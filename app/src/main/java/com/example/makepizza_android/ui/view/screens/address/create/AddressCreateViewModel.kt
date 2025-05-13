package com.example.makepizza_android.ui.view.screens.address.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.usecases.address.CreateAddress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddressCreateViewModel : ViewModel() {
    private val _shippingAddressValue = MutableLiveData<String>()
    val shippingAddressValue: LiveData<String> = _shippingAddressValue

    private val _shippingAddressName = MutableLiveData<String>()
    val shippingAddressName: LiveData<String> = _shippingAddressName

    private val _showCurrentTitle = MutableLiveData<Boolean>(false)
    val showCurrentTitle: LiveData<Boolean> = _showCurrentTitle

    private val _uiState = MutableStateFlow<AddressCreateViewState>(AddressCreateViewState.Nothing)
    val uiState = _uiState.asStateFlow()

    private val createAddressUseCase = CreateAddress()

    fun handleAddressValueOnChange(value: String) {
        _shippingAddressValue.value = value
    }

    fun handleAddressNameOnChange(value: String) {
        _shippingAddressName.value = value
        _showCurrentTitle.value = value.isNotEmpty()
    }

    fun handleCreateNewAddress(userID: String) {
        viewModelScope.launch { createNewAddress(userID) }
    }

    private suspend fun createNewAddress(userID: String) {
        val name = _shippingAddressName.value
        val value = _shippingAddressValue.value

        _uiState.value = AddressCreateViewState.Loading

        if (name == null || name.isEmpty()) {
            _uiState.value = AddressCreateViewState.Success
            return
        }
        if (value == null || value.isEmpty()) {
            _uiState.value = AddressCreateViewState.Success
            return
        }

        createAddressUseCase(Address(0, name, value, userID))
        _uiState.value = AddressCreateViewState.Success
    }
}