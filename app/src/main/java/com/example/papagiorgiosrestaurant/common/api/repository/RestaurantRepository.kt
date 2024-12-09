package com.example.papagiorgiosrestaurant.common.api.repository

import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantRepository {

    fun getRestaurants(
        onSuccess: (List<Restaurant>) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)

        service.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(
                call: Call<List<Restaurant>>,
                response: Response<List<Restaurant>>
            ) {
                if (response.isSuccessful) {
                    val restaurants = response.body() ?: emptyList()
                    onSuccess(restaurants)
                } else {
                    onError(Throwable("Error al obtener los restaurantes: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                onError(t)
            }
        })

    }



}