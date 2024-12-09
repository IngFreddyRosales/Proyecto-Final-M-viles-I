package com.example.papagiorgiosrestaurant.common.api.repository

import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object UserRepository {

    private val apiService: ApiServices by lazy {
        RetrofitRepository.getRetrofitInstance().create(ApiServices::class.java)
    }




}