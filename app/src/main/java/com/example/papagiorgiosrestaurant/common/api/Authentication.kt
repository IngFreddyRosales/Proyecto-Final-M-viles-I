package com.example.papagiorgiosrestaurant.common.api

import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import okhttp3.Interceptor
import okhttp3.Response

class Authentication : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MainActivity.access_token
        val requestBuilder = chain.request().newBuilder()

        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }


}