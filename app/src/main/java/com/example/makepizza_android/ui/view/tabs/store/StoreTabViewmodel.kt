package com.example.makepizza_android.ui.view.tabs.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.repository.PizzaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreTabViewmodel : ViewModel() {
    private val _address = MutableLiveData<String?>()
    val address: LiveData<String?> = _address

    private val _pizzas = MutableLiveData<List<PizzaListModel>>()
    val pizzas: LiveData<List<PizzaListModel>> = _pizzas

    private val _ingredients = MutableLiveData<List<IngredientListModel>>()
    val ingredients: LiveData<List<IngredientListModel>> = _ingredients

    private val _uiState = MutableStateFlow<StoreTabState>(StoreTabState.Loading)
    val uiState: StateFlow<StoreTabState> = _uiState.asStateFlow()

    private val pizzaRepository = PizzaRepository()

    init {
        viewModelScope.launch {
            try {
                fetchPizzas()
                fetchIngredients()
                _uiState.value = StoreTabState.Success
            } catch (ex: Exception) {
                _uiState.value = StoreTabState.Error(ex.message!!)
            }
        }
    }

    fun handleAddressClick() {
        _address.value = "Carrera 7"
    }

    private suspend fun fetchPizzas() {
        val result = pizzaRepository.getAllPizzas()
        _pizzas.postValue(result)
    }

    private suspend fun fetchIngredients() {
        val result = pizzaRepository.getAllIngredients()
        _ingredients.postValue(result)
    }
}