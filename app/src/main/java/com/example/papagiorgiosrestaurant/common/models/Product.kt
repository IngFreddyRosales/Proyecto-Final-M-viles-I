package com.example.papagiorgiosrestaurant.common.models

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val restaurant_id: Int,
    val image: String
)
