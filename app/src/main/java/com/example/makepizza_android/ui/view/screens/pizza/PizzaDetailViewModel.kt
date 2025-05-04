package com.example.makepizza_android.ui.view.screens.pizza

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.models.PizzaModel
import com.example.makepizza_android.data.repository.PizzaRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

    fun fetchPizzaModel(uid: String, isCustom: Boolean) {
        if (isCustom) {
            viewModelScope.launch { fetchCustomPizzaModelORError(uid) }
        }
        else {
            viewModelScope.launch { fetchPizzaModelORError(uid) }
        }
    }

    fun handleAddToCartClick() {

    }

    private suspend fun fetchPizzaModelORError(uid: String) {
        _uiState.value = PizzaDetailViewState.Loading

        try {
            val pizza = pizzaRepository.getPizza(uid)

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

    private suspend fun fetchCustomPizzaModelORError(uid: String) {
        _uiState.value = PizzaDetailViewState.Loading

        Firebase.auth.currentUser?.getIdToken(false).also {
            it?.addOnSuccessListener {
               Log.d("PizzaDetailViewModel", it.token.toString())
            }
        }
        try {
            val pizza = pizzaRepository.getCustomPizza(uid)

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
}