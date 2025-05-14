package com.example.makepizza_android.ui.view.tabs.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makepizza_android.data.remote.models.IngredientListModel
import com.example.makepizza_android.data.remote.models.PizzaListModel
import com.example.makepizza_android.data.repository.IngredientRepository
import com.example.makepizza_android.data.repository.PizzaRepository
import com.example.makepizza_android.domain.models.Address
import com.example.makepizza_android.domain.usecases.address.GetAddressByID
import com.example.makepizza_android.domain.usecases.user.CurrentUser
import com.example.makepizza_android.domain.usecases.user.UpdateUserDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreTabViewmodel : ViewModel() {
    private val _currentAddress = MutableLiveData<Address?>()
    val currentAddress: LiveData<Address?> = _currentAddress

    private val _savedAddresses = MutableLiveData<List<Address>>()
    val savedAddresses: LiveData<List<Address>> = _savedAddresses

    private val _pizzas = MutableLiveData<List<PizzaListModel>>()
    val pizzas: LiveData<List<PizzaListModel>> = _pizzas

    private val _ingredients = MutableLiveData<List<IngredientListModel>>()
    val ingredients: LiveData<List<IngredientListModel>> = _ingredients

    private val _showAddressesModal = MutableLiveData<Boolean>(false)
    val showAddressesModal: LiveData<Boolean> = _showAddressesModal

    private val _uiState = MutableStateFlow<StoreTabState>(StoreTabState.Loading)
    val uiState: StateFlow<StoreTabState> = _uiState.asStateFlow()

    private val currentUserUseCase = CurrentUser()

    private val updateUserDBUseCase = UpdateUserDB()

    private val getAddressByIDUseCase = GetAddressByID()


    private val pizzaRepository = PizzaRepository()

    private val ingredientRepository = IngredientRepository()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch { fetchDataImp() }
        viewModelScope.launch { loadUserAddressImp() }
        viewModelScope.launch { loadSavedAddressesImp() }
    }

    fun handleOnDismissRequest() {
        _showAddressesModal.value = false
    }

    fun handleAddressClick() {
        _showAddressesModal.value = true
    }

    fun handleAddressSelected(addressID: Int) {
        viewModelScope.launch { handleAddressSelectedImp(addressID) }
        _showAddressesModal.value = false
    }

    private suspend fun handleAddressSelectedImp(addressID: Int) {
        _uiState.value = StoreTabState.Loading

        val address = getAddressByIDUseCase(addressID)
        val user = currentUserUseCase()

        if (user != null) {
            user.address = address
            updateUserDBUseCase(user)
        }
        _currentAddress.value = address
        _showAddressesModal.value = false
        _uiState.value = StoreTabState.Success
    }

    private suspend fun fetchDataImp() {
        try {
            _uiState.value = StoreTabState.Loading
            fetchIngredientsImp()
            fetchPizzasImp()
            _uiState.value = StoreTabState.Success
        } catch (ex: Exception) {
            _uiState.value = StoreTabState.Error(ex.message!!)
        }
    }

    private suspend fun loadUserAddressImp() {
        val user = currentUserUseCase()
        val userAddress = user?.address
        _currentAddress.postValue(userAddress)
    }

    private suspend fun loadSavedAddressesImp() {
        val user = currentUserUseCase()
        val addressesList = user?.addresses
        _savedAddresses.postValue(addressesList ?: emptyList())
    }

    private suspend fun fetchPizzasImp() {
        val result = pizzaRepository.getAllPizzas()
        _pizzas.postValue(result)
    }

    private suspend fun fetchIngredientsImp() {
        val result = ingredientRepository.getAllIngredients()
        _ingredients.postValue(result)
    }
}