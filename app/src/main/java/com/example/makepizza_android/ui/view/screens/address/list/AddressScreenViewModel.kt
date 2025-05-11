package com.example.makepizza_android.ui.view.screens.address.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.usecases.address.GetAllAddressUseCase
import kotlinx.coroutines.launch

class AddressScreenViewModel() : ViewModel() {
    private val _addressesList = MutableLiveData<List<Address>>(emptyList())
    val addressesList: LiveData<List<Address>> = _addressesList

    private val getAllAddressUseCase = GetAllAddressUseCase()

    fun getAllUserAddresses(userID: String) {
        viewModelScope.launch { fetchAllUserAddresses(userID) }
    }

    private suspend fun fetchAllUserAddresses(userID: String) {
        val orders = getAllAddressUseCase(ownerID = userID)
        _addressesList.postValue(orders)
    }
}