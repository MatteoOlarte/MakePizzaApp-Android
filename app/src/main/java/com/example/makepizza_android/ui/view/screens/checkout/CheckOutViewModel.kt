package com.example.makepizza_android.ui.view.screens.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.models.Order
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.usecases.cart.ClearCartUseCase
import com.example.makepizza_android.domain.usecases.orders.CreateOrderUseCase
import com.example.makepizza_android.domain.usecases.orders.EditAddressUseCase
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckOutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<CheckOutViewState>(CheckOutViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _currentOrder = MutableLiveData<Order?>(null)
    val currentOrder: LiveData<Order?> = _currentOrder

    private val _currentUser = MutableLiveData<User?>(null)
    val currentUser: LiveData<User?> = _currentUser

    private val _fName = MutableLiveData<String>("")
    val fName: LiveData<String> = _fName

    private val _lName = MutableLiveData<String>("")
    val lName: LiveData<String> = _lName

    private val _address = MutableLiveData<String>("")
    val address: LiveData<String> = _address

    private val _phone = MutableLiveData<String>("")
    val phone: LiveData<String> = _phone

    private val _tip = MutableLiveData<Double>(2000.0)
    val tip: LiveData<Double> = _tip

    private val _total = MutableLiveData<Double>(0.0)
    val total: LiveData<Double> = _total

    private val currentUserUseCase = CurrentUser()

    private val createOderUseCase = CreateOrderUseCase()

    private val editAddressUseCase = EditAddressUseCase()

    private val clearCartUseCase = ClearCartUseCase()

    fun fetchData() {
        viewModelScope.launch { fetchDataImpl() }
    }

    private suspend fun fetchDataImpl() {
        _currentUser.value = currentUserUseCase()
        _address.value = _currentUser.value?.address?.addressValue
    }

    fun createNewOrder() {
        viewModelScope.launch { createNewOrderImpl() }
    }

    private suspend fun createNewOrderImpl() {
        _uiState.value = CheckOutViewState.Loading
        val user: User = currentUserUseCase()!!
        val address: Address? = user.address
        val order: Order?

        if (address == null) {
            _uiState.value = CheckOutViewState.OnError("InvalidAddress")
            return
        }

        try {
            order = createOderUseCase(address, user.shoppingCart)
            _currentOrder.value = order
            _total.value = order?.totalPrice?.toDouble() ?: 0.0
            _uiState.value = CheckOutViewState.Success
        } catch (err: Exception) {
            _uiState.value = CheckOutViewState.OnError("NetworkError")
        }
    }

    fun handleDoneClick() {
        viewModelScope.launch { handleDoneClickImpl() }
    }

    private suspend fun handleDoneClickImpl() {
        _uiState.value = CheckOutViewState.Loading

        val order = _currentOrder.value
        val addressValue = _address.value
        val tipValue = _tip.value

        if (addressValue == null || addressValue.isEmpty()) {
            _uiState.value = CheckOutViewState.OnError("Address Is Null")
            return
        }

        if (tipValue == null) {
            _uiState.value = CheckOutViewState.OnError("Tip Is Null")
            return
        }

        try {
            if (order !== null) {
                editAddressUseCase(order.uid, addressValue, tipValue.toFloat() / 4200)
                clearCartUseCase(currentUser.value!!.uid)
                _uiState.value = CheckOutViewState.Updated
            } else {
                _uiState.value = CheckOutViewState.OnError("Order is Null")
            }
        } catch (err: Exception) {
            _uiState.value = CheckOutViewState.OnError(err.message.toString())
        }
    }

    fun handleFNameFieldChanged(value: String) {
        _fName.value = value
    }

    fun handleLNameFieldChanged(value: String) {
        _lName.value = value
    }

    fun handleAddressFieldChanged(value: String) {
        _address.value = value
    }

    fun handlePhoneFieldChanged(value: String) {
        _phone.value = value
    }

    fun handleTipFieldChanged(value: Double) {
        val current = _currentOrder.value?.totalPrice?.toDouble() ?: 0.0

        _tip.value = value
        _total.value = current.plus(value / 4200)
    }
}