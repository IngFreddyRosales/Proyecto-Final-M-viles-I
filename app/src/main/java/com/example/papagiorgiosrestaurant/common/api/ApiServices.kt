package com.example.papagiorgiosrestaurant.common.api

import com.example.papagiorgiosrestaurant.common.models.LoginRequest
import com.example.papagiorgiosrestaurant.common.models.Order
import com.example.papagiorgiosrestaurant.common.models.OrderResponse
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.common.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST("users")
    fun registerUser(@Body user: User): Call<Void>

    @GET("me")
    fun getInfoUser(
        @Header("Authorization") token: String): Call<User>

    @POST("users/login")
    fun loginUser(@Body loginRequest: LoginRequest)
    : Call<User>

    @GET("restaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getMenu(
        @Path("id") restaurantId: Int,
        @Header("Authorization") token: String
    ): Call<Restaurant>

    @POST("orders")
    fun placeOrder(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<Void>

    @GET("orders/free")
    fun getOrders(
        @Header("Authorization") token: String
    ): Call<List<OrderResponse>>

    @GET("restaurants/{id}")
    fun getRestaurantById(
        @Path("id") restaurantId: Int,
        @Header("Authorization") token: String
    ): Call<Restaurant>

    @GET("orders/{id}")
    fun getOrderById(
        @Path("id") orderId: Int,
        @Header("Authorization") token: String
    ): Call<OrderResponse>

    @POST("orders/{id}/accept")
    fun acceptOrder(
        @Path("id") orderId: Int,
        @Header("Authorization") token: String
    ): Call<Void>



}