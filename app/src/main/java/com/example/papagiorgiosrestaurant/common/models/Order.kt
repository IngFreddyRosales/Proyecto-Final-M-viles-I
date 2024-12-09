package com.example.papagiorgiosrestaurant.common.models

data class Order(
    val restaurant_id: Int,
    val total: Double,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val details: List<OrderDetail>
)
