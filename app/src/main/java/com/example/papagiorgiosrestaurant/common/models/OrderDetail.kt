package com.example.papagiorgiosrestaurant.common.models

data class OrderDetail(
    val product_id: Int,
    val qty: Int,
    val price: Double,
    val product: Product
)
