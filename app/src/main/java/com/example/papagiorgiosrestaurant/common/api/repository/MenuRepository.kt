package com.example.papagiorgiosrestaurant.common.api.repository

import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.models.Product
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MenuRepository {

    fun getMenuByRestaurantId(
        restaurant_id: Int,
        onSuccess: (List<Product>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.getMenu(restaurant_id, token).enqueue(object : Callback<Restaurant> {
            override fun onResponse(
                call: Call<Restaurant>,
                response: Response<Restaurant>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { restaurant ->
                        onSuccess(restaurant.products)
                    } ?: onError(Throwable("Error: Lista de productos vacia"))
                } else {
                    onError(Throwable("Error al obtener el menu: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                onError(t)
            }

        })

    }
}