package com.example.papagiorgiosrestaurant.common.models

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val ownerId: Int,
    val logo: String,
    val products: List<Product>
)
