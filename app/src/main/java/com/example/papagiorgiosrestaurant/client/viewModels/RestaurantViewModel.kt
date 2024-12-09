package com.example.papagiorgiosrestaurant.client.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.RestaurantRepository
import com.example.papagiorgiosrestaurant.common.models.Restaurant

class RestaurantViewModel: ViewModel() {

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurant: LiveData<List<Restaurant>> get() = _restaurants

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getRestaurants() {
        RestaurantRepository.getRestaurants(
            onSuccess = { restaurantList ->
                _restaurants.value = restaurantList
            },
            onError = { throwable ->
                _error.value = throwable.message
            }
        )

    }
}