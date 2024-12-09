package com.example.papagiorgiosrestaurant.client.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.MenuRepository
import com.example.papagiorgiosrestaurant.common.models.Product

class MenuListViewModel: ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getMenu(restaurantId: Int) {
        println("Restaurant ID VM: $restaurantId")
        MenuRepository.getMenuByRestaurantId(restaurantId,
            onSuccess = { productList ->
                _products.value = productList
            },
            onError = { throwable ->
                _error.value = throwable.message
            }
        )
    }
}