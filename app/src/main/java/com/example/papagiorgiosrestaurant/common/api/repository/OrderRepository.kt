package com.example.papagiorgiosrestaurant.common.api.repository

import android.util.Log
import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.api.repository.RestaurantRepository.getRestaurants
import com.example.papagiorgiosrestaurant.common.models.Order
import com.example.papagiorgiosrestaurant.common.models.OrderResponse
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderRepository {

    fun placeOrder(
        orderRequest: Order, onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.placeOrder(orderRequest, token).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error al realizar la orden: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })

    }

    fun getOrders(
        onSuccess: (List<OrderResponse>, List<Restaurant>) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.getOrders(token).enqueue(object : Callback<List<OrderResponse>> {
            override fun onResponse(
                call: Call<List<OrderResponse>>,
                response: Response<List<OrderResponse>>
            ) {
                if (response.isSuccessful) {
                    println("ordenes obtenidas: ${response.body()}")
                    response.body()?.let { orderList ->
                        getRestraurantsList(
                            onSuccess = { restaurantList ->
                                onSuccess(orderList, restaurantList)
                            },
                            onError = { error ->
                                onError(error)
                            }
                        )
                    }
                } else {
                    onError(Throwable("Error al obtener las ordenes: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<OrderResponse>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getRestraurantsList(
        onSuccess: (List<Restaurant>) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)

        service.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { restaurantList ->
                        onSuccess(restaurantList)
                    }
                } else {
                    onError(Throwable("Error al obtener los restaurantes: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getOrderById(
        orderId: Int,
        onSuccess: (OrderResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        println("OrderId: $orderId")
        Log.d("OrderRepository", "Requesting order with ID: $orderId")


        service.getOrderById(orderId, token).enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { order ->
                        Log.d("OrderRepository", "Order retrieved successfully: $order")

                        onSuccess(order)
                    }
                } else {
                    onError(Throwable("Error al obtener la orden: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getRestaurantById(
        restaurantId: Int,
        onSuccess: (Restaurant) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.getRestaurantById(restaurantId,token).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    response.body()?.let { restaurant ->
                        onSuccess(restaurant)
                    }
                } else {
                    onError(Throwable("Error al obtener el restaurante: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun acceptOrder(
        orderId: Int,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.acceptOrder(orderId, token).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(Throwable("Error al aceptar la orden: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }




}