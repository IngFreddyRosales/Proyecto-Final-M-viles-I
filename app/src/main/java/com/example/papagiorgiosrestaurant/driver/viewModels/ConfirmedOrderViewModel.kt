package com.example.papagiorgiosrestaurant.driver.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.OrderRepository
import com.example.papagiorgiosrestaurant.common.api.repository.UserRepository
import com.example.papagiorgiosrestaurant.common.models.OrderDetail
import com.example.papagiorgiosrestaurant.common.models.OrderResponse
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.common.models.User

class ConfirmedOrderViewModel : ViewModel() {

    private val _orderDetails = MutableLiveData<OrderResponse>()
    val orderDetails: LiveData<OrderResponse> get() = _orderDetails

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> get() = _restaurant

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchOrderDetails(orderId: Int) {
        OrderRepository.getOrderById(orderId,
            onSuccess = { orderResponse ->
                _orderDetails.postValue(orderResponse)
                OrderRepository.getRestaurantById(orderResponse.restaurant_id,
                    onSuccess = { restaurant ->
                        _restaurant.postValue(restaurant)
                    },
                    onError = { error ->
                        _error.postValue(error.message)
                    }
                )
            },
            onError = { throwable ->
                _error.postValue(throwable.message)
            }
        )

    }

    fun acceptOrder(orderId: Int) {
        OrderRepository.acceptOrder(orderId,
            onSuccess = {
                fetchOrderDetails(orderId)
            },
            onError = { throwable ->
                _error.postValue(throwable.message)
            }
        )
    }

}
