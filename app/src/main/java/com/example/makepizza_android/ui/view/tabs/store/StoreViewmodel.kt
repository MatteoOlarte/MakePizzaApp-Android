package com.example.makepizza_android.ui.view.tabs.store

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.models.IngredientListModel
import com.example.makepizza_android.data.models.PizzaListModel
import com.example.makepizza_android.data.repository.PizzaRepository
import kotlinx.coroutines.launch

class StoreViewmodel : ViewModel() {
    private val pizzaRepository = PizzaRepository()

    private val _address = MutableLiveData<String?>()
    val address: LiveData<String?> = _address

    private val _pizzas = MutableLiveData<List<PizzaListModel>>()
    val pizzas: LiveData<List<PizzaListModel>> = _pizzas

    private val _ingredients = MutableLiveData<List<IngredientListModel>>()
    val ingredients: LiveData<List<IngredientListModel>> = _ingredients

    init {
        this.fetchPizzas()
        this.fetchIngredients()
    }

    fun handleAddressClick() {
        _address.value = "Carrera 7"
    }

    fun fetchPizzas() {
        viewModelScope.launch {
            try {
                val result = pizzaRepository.getAllPizzas()
                _pizzas.postValue(result)
            } catch (ex: Exception) {
                Log.d("StoreViewmodel", ex.message!!)
            }
        }
    }

    fun fetchIngredients() {
        viewModelScope.launch {
            try {
                val result = pizzaRepository.getAllIngredients()
                _ingredients.postValue(result)
            } catch (ex: Exception) {
                Log.d("StoreViewmodel", ex.message!!)
            }
        }
    }
}