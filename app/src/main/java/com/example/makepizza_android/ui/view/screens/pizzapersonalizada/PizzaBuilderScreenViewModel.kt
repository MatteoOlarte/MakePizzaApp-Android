package com.example.makepizza_android.ui.view.screens.pizzapersonalizada

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.repository.IngredientRepository
import com.example.makepizza_android.data.repository.PizzaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PizzaBuilderScreenViewModel: ViewModel() {
    private val _ingredients = MutableLiveData<List<IngredientListModel>>(emptyList())
    val ingredients: LiveData<List<IngredientListModel>> = _ingredients

    private val _sauces = MutableLiveData<List<IngredientListModel>>(emptyList())
    val sauces: LiveData<List<IngredientListModel>> = _sauces

    private val _drugs = MutableLiveData<List<IngredientListModel>>(emptyList())
    val drugs: LiveData<List<IngredientListModel>> = _drugs

    private val _uiSate = MutableStateFlow<PizzaBuilderScreenViewState>(PizzaBuilderScreenViewState.Loading)

    private val ingredientRepository = IngredientRepository()

    private val pizzaRepository = PizzaRepository()

    fun fetchData() {
        viewModelScope.launch { fetchDataImpl() }
    }

    private suspend fun fetchDataImpl() {
        _uiSate.value = PizzaBuilderScreenViewState.Loading
        val ingredients = ingredientRepository.getAllIngredients()

        _sauces.value = ingredients.filter { it.type == "sauce" }
        _drugs.value = ingredients.filter { it.type == "dough" }
        _ingredients.value = ingredients.filter { it.type == "ingredient" }

        _uiSate.value = PizzaBuilderScreenViewState.Success
    }
}