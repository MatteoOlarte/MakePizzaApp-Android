package com.example.makepizza_android.ui.view.tabs.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.CartItem
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.usecases.cart.ClearShoppingCartOf
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartTabViewModel : ViewModel() {
    private val _items = MutableLiveData<Map<CartItem, Boolean>>(emptyMap())
    val items: LiveData<Map<CartItem, Boolean>> = _items

    private val _itemsLength = MutableLiveData<Int>(0)
    val itemsLength: LiveData<Int> = _itemsLength

    private val _total = MutableLiveData<Double>(0.0)
    val total: LiveData<Double> = _total

    private val _uiState = MutableStateFlow<CartTabViewState>(CartTabViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val currentUserUseCase = CurrentUser()

    private val clearShoppingCartUseCase = ClearShoppingCartOf()

    fun fetchData() {
        viewModelScope.launch { fetchDataImpl() }
    }

    private suspend fun fetchDataImpl() {
        _uiState.value = CartTabViewState.Loading
        val current: User? = currentUserUseCase()
        val cart: Map<CartItem, Boolean>

        if (current == null) {
            _uiState.value = CartTabViewState.Error
            return
        }

        cart = current.cartItems.associateWith { true }
        _items.value = cart
        _total.value = cart.calculateTotal()
        _itemsLength.value = cart.itemsCheckedSize()
        _uiState.value = CartTabViewState.Success
    }

    fun handleClearClick() {
        viewModelScope.launch { handleClearClickImpl() }
    }

    private suspend fun handleClearClickImpl() {
        _uiState.value = CartTabViewState.Loading
        val current: User? = currentUserUseCase()

        if (current == null) {
            _uiState.value = CartTabViewState.Error
            return
        }

        clearShoppingCartUseCase(current.uid)
        _total.value = 0.0
        _itemsLength.value = 0
        _uiState.value = CartTabViewState.Success
    }


    fun handleDeleteItemClick(cartItem: CartItem) {
        viewModelScope.launch { handleDeleteItemClickImpl(cartItem) }
    }

    private suspend fun handleDeleteItemClickImpl(cartItem: CartItem) {
        _uiState.value = CartTabViewState.Loading
        val cart = _items.value?.toMutableMap()?.apply { this.remove(cartItem) }

        if (cart != null) {
            _items.value = cart
            _total.value = cart.calculateTotal()
            _itemsLength.value = cart.itemsCheckedSize()
        }
        _uiState.value = CartTabViewState.Success
    }

    fun handleOnItemChecked(cartItem: CartItem, value: Boolean) {
        handleOnItemCheckedImpl(cartItem, value)
    }

    private fun handleOnItemCheckedImpl(cartItem: CartItem, value: Boolean) {
        val cart = _items.value?.toMutableMap()?.apply { this[cartItem] = value }

        if (cart != null) {
            _items.value = cart
            _total.value = cart.calculateTotal()
            _itemsLength.value = cart.itemsCheckedSize()
        }
    }

    private fun Map<CartItem, Boolean>.calculateTotal(): Double {
        return this.filter { it.value }.keys.sumOf { it.price.toDouble() }
    }

    private fun Map<CartItem, Boolean>.itemsCheckedSize(): Int {
        return this.filter { it.value }.size
    }
}