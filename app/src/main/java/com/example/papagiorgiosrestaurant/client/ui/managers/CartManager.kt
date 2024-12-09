package com.example.papagiorgiosrestaurant.client.ui.managers

import com.example.papagiorgiosrestaurant.common.models.Order
import com.example.papagiorgiosrestaurant.common.models.OrderDetail
import com.example.papagiorgiosrestaurant.common.models.Product

object CartManager {

    private val cartItems = mutableListOf<Pair<Product, Int>>()

    fun addProduct(product: Product, quantity: Int) {
        val existingItem = cartItems.find { it.first == product }
        if (existingItem != null){
            val updatedItem = existingItem.copy(second = existingItem.second + quantity)
            cartItems[cartItems.indexOf(existingItem)] = updatedItem
            println(cartItems)
        } else {
            cartItems.add(product to quantity)
        }
    }

    fun updateQuantity(product: Product, newQuantity: Int) {
        val existingItem = cartItems.find { it.first == product }
        if(existingItem != null){
            if (newQuantity > 0){
                cartItems[cartItems.indexOf(existingItem)] = existingItem.copy(second = newQuantity)
                println(cartItems)
            }else{
                cartItems.remove(existingItem)
            }
        }
    }

    fun OrderRequest(restaurant_id: Int, address: String, latitude: String, longitude: String): Order {
        val total = cartItems.sumOf { it.first.price * it.second }
        val details = cartItems.map { OrderDetail(it.first.id, it.second, it.first.price, it.first) }
        println("Total: $total")
        return Order(restaurant_id, total, address, latitude.toDouble(), longitude.toDouble(), details)
    }

    fun removeProduct(product: Product) {
        cartItems.removeIf { it.first == product }
    }

    fun getCartItems(): List<Pair<Product, Int>>  = cartItems

    fun clearCart() {
        cartItems.clear()
    }

}