package com.example.papagiorgiosrestaurant.driver.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.OrderRepository
import com.example.papagiorgiosrestaurant.common.models.OrderResponse
import com.example.papagiorgiosrestaurant.common.models.Restaurant

class OrderListViewModel: ViewModel() {

    private val _orderResponses = MutableLiveData<List<OrderResponse>>()
    val orderResponses: LiveData<List<OrderResponse>> get() = _orderResponses

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOrders(){
        OrderRepository.getOrders(
            onSuccess = { orderList, restaurantList ->
                _orderResponses.postValue(orderList)
                _restaurants.postValue(restaurantList)
            },
            onError = { throwable ->
                _error.postValue(throwable.message)
            }
        )
    }
}