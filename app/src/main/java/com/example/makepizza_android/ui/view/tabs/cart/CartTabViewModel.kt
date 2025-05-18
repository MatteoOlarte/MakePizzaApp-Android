package com.example.makepizza_android.ui.view.tabs.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.Cart
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.usecases.cart.DeleteItem
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartTabViewModel : ViewModel() {
    private val _cart = MutableLiveData<Map<Cart, Boolean>>(emptyMap())
    val cart: LiveData<Map<Cart, Boolean>> = _cart

    private val _cartLength = MutableLiveData<Int>(0)
    val cartLength: LiveData<Int> = _cartLength

    private val _total = MutableLiveData<Double>(0.0)
    val total: LiveData<Double> = _total

    private val _uiState = MutableStateFlow<CartTabViewState>(CartTabViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val currentUserUseCase = CurrentUser()

    private val deleteCartItemUseCase = DeleteItem()

    fun fetchData() {
        viewModelScope.launch { fetchDataImpl() }
    }

    private suspend fun fetchDataImpl() {
        _uiState.value = CartTabViewState.Loading
        val current: User? = currentUserUseCase()
        val data: Map<Cart, Boolean>

        if (current == null) {
            _uiState.value = CartTabViewState.Error
            return
        }

        data = current.shoppingCart.map { it }.associateWith { true }
        _cart.value = data
        _total.value = data.calculateTotal()
        _cartLength.value = data.itemsCheckedSize()
        _uiState.value = CartTabViewState.Success
    }

    fun handleDeleteItemClick(data: Cart) {
        viewModelScope.launch { handleDeleteItemClickImpl(data) }
    }

    private suspend fun handleDeleteItemClickImpl(data: Cart) {
        _uiState.value = CartTabViewState.Loading
        val cart = _cart.value?.toMutableMap()?.apply { this.remove(data) }

        if (cart != null) {
            deleteCartItemUseCase(data.id)
            _cart.value = cart
            _total.value = cart.calculateTotal()
            _cartLength.value = cart.itemsCheckedSize()
        }
        _uiState.value = CartTabViewState.Success
    }

    fun handleOnItemChecked(data: Cart, value: Boolean) {
        handleOnItemCheckedImpl(data, value)
    }

    private fun handleOnItemCheckedImpl(data: Cart, value: Boolean) {
        val cart = _cart.value?.toMutableMap()?.apply { this[data] = value }

        if (cart != null) {
            _cart.value = cart
            _total.value = cart.calculateTotal()
            _cartLength.value = cart.itemsCheckedSize()
        }
    }

    private fun Map<Cart, Boolean>.calculateTotal(): Double {
        return this.filter { it.value }.keys.sumOf { it.item.price.toDouble() }
    }

    private fun Map<Cart, Boolean>.itemsCheckedSize(): Int {
        return this.filter { it.value }.size
    }
}