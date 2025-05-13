package com.example.makepizza_android.ui.view.screens.address.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import kotlinx.coroutines.launch

class AddressScreenViewModel() : ViewModel() {
    private val _addressesList = MutableLiveData<List<Address>>(emptyList())
    val addressesList: LiveData<List<Address>> = _addressesList

    private val currentUserUseCase = CurrentUser()

    fun getAllUserAddresses() {
        viewModelScope.launch { fetchAllUserAddresses() }
    }

    private suspend fun fetchAllUserAddresses() {
        val current = currentUserUseCase()

        if (current != null) {
            _addressesList.value = current.addresses
        }
    }
}