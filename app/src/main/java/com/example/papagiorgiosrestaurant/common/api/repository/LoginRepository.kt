package com.example.papagiorgiosrestaurant.common.api.repository

import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.models.LoginRequest
import com.example.papagiorgiosrestaurant.common.models.User
import com.example.papagiorgiosrestaurant.common.models.users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {
    fun loginUser(
        loginRequest: LoginRequest,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)

        service.loginUser(loginRequest).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    MainActivity.access_token = response.body()?.access_token.toString()

                    println("LoginRepository: ${loginRequest.email} ${loginRequest.password}")
                    println(MainActivity.access_token + "Access_Token")
                    onSuccess()
                } else {
                    onError(Throwable("Error al iniciar sesion: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onError(t)
            }
        })
    }

    fun getInfoUser(
        onSuccess: (Int) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)
        val token = "Bearer ${MainActivity.access_token}"

        service.getInfoUser(token).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        println("respuesta del response body: ${response.body()}")
                        onSuccess(user.profile.role)
                    } ?: onError(Throwable("Error: Respuesta de usuario vacía"))
                } else {
                    onError(Throwable("Error al obtener la información del usuario: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onError(t)
            }
        })
    }




}
