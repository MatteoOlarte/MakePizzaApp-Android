package com.example.makepizza_android.ui.view.screens.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.domain.models.OrderList
import com.example.makepizza_android.domain.models.User
import com.example.makepizza_android.domain.usecases.orders.GetAllCurrentUserOrdersUseCase
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderScreenViewModel: ViewModel() {
    private val _orders = MutableLiveData<List<OrderList>>()
    val orders: LiveData<List<OrderList>> = _orders

    private val _uiState = MutableStateFlow<OrderScreenViewState>(OrderScreenViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private val currentUserUseCase = CurrentUser()

    private val getAllOrdersFromCurrentUserOrdersUseCase = GetAllCurrentUserOrdersUseCase()

    fun fetchData() {
        viewModelScope.launch { fetchDataImpl() }
    }

    private suspend fun fetchDataImpl() {
        _uiState.value = OrderScreenViewState.Loading
        val currentUser: User? = currentUserUseCase()

        if (currentUser == null) {
            _uiState.value = OrderScreenViewState.OnError(err = "LoginRequired")
            return
        }

        try {
            val orders = getAllOrdersFromCurrentUserOrdersUseCase()
            _orders.value = orders
            Log.d("OrderScreenViewModel", orders.toString())
            _uiState.value = OrderScreenViewState.Success
        } catch (err: Exception) {
            Log.e("OrderScreenViewModel", "$err")
            _uiState.value = OrderScreenViewState.OnError("NetworkError")
        }
    }
}