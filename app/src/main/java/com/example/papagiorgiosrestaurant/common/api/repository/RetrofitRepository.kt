package com.example.papagiorgiosrestaurant.common.api.repository

import com.example.papagiorgiosrestaurant.common.api.Authentication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRepository {
    private const val BASE_URL = "https://proyectodelivery.jmacboy.com/api/"

    fun getRetrofitInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(Authentication())
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }
}