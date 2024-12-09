package com.example.papagiorgiosrestaurant.common.api.repository

import android.widget.Toast
import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.common.api.ApiServices
import com.example.papagiorgiosrestaurant.common.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegisterRepository {
    fun registerUser(
        user: User,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ApiServices::class.java)


        println("RegisterRepository: ${user.name} ${user.email} ${user.password} ")


        service.registerUser(user).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onSuccess()
                    println("Usuario registrado en repository")
                    println(MainActivity.access_token + "Access_Token")
                } else {
                    onError(Throwable("Error al registrar usuario: ${response.code()} ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })
    }
}