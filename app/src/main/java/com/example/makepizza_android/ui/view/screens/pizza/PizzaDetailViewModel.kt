package com.example.makepizza_android.ui.view.screens.pizza

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.remote.models.PizzaModel
import com.example.makepizza_android.data.repository.PizzaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PizzaDetailViewModel : ViewModel() {
    private val _currentPizzaModel = MutableLiveData<PizzaModel>()
    val currentPizzaModel: LiveData<PizzaModel> = _currentPizzaModel

    private val _uiState = MutableStateFlow<PizzaDetailViewState>(PizzaDetailViewState.Loading)
    val uiState: StateFlow<PizzaDetailViewState> = _uiState.asStateFlow()

    private val pizzaRepository = PizzaRepository()

    fun fetchData(uid: String, isCustom: Boolean) {
        viewModelScope.launch { fetchDataImpl(uid, isCustom) }
    }

    fun handleAddToCartClick() {
        _uiState.value = PizzaDetailViewState.CartClicked
    }

    private suspend fun fetchDataImpl(uid: String, isCustom: Boolean) {
        _uiState.value = PizzaDetailViewState.Loading

        try {
            val pizza = if (isCustom) {
                pizzaRepository.getCustomPizza(uid)
            } else {
                pizzaRepository.getPizza(uid)
            }

            if (pizza != null) {
                _currentPizzaModel.value = pizza
                _uiState.value = PizzaDetailViewState.Success
                return
            }
            _uiState.value = PizzaDetailViewState.Error
        } catch (ex: Exception) {
            Log.e("PizzaDetailViewModel", ex.message!!)
            _uiState.value = PizzaDetailViewState.Error
        }
    }

    private suspend fun addItemToCartImpl() {

    }
}