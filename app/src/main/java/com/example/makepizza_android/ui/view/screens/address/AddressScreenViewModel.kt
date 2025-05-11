package com.example.makepizza_android.ui.view.screens.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressScreenViewModel : ViewModel() {
    private val _showBottomSheet = MutableLiveData<Boolean>(false)
    val showBottomSheet: LiveData<Boolean> = _showBottomSheet

    fun handleOnDismissRequest() {
        _showBottomSheet.value = false
    }

    fun setBottomSheetVisibility(value: Boolean) {
        _showBottomSheet.value = value
    }
}