package com.example.papagiorgiosrestaurant.client.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.adapters.CartAdapter
import com.example.papagiorgiosrestaurant.client.ui.managers.CartManager
import com.example.papagiorgiosrestaurant.common.api.repository.OrderRepository
import com.example.papagiorgiosrestaurant.common.api.repository.RestaurantRepository
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var restaurants: List<Restaurant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        calculeTotal()


//            Toast.makeText(this, "Orden realizada", Toast.LENGTH_SHORT).show()
//            CartManager.clearCart()
//            finish()
        binding.btnCheckout.setOnClickListener {
            fetchRestaurants()
        }
    }

    private fun fetchRestaurants() {
        println("Metodo fetchRestaurants")
        RestaurantRepository.getRestaurants(
            onSuccess = { restaurantsList ->
                restaurants = restaurantsList
                placeOrder()
            },
            onError = { errorMessage ->
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun placeOrder() {
        println("Metodo placeOrder")
        val cartItems = CartManager.getCartItems()
        if(cartItems.isNotEmpty()){
            val firstProduct = cartItems[0].first
            val restaurantId = firstProduct.restaurant_id
            val restaurantIndex = restaurants.indexOfFirst { it.id == restaurantId }

            if (restaurantIndex != -1) {
            val selectedRestaurant = restaurants[restaurantIndex]
                val orderRequest =CartManager.OrderRequest(
                    restaurant_id = selectedRestaurant.id,
                    address = selectedRestaurant.address,
                    latitude = selectedRestaurant.latitude.toString(),
                    longitude = selectedRestaurant.longitude.toString()
                )

                println("Datos de la orden: ${orderRequest}")

                OrderRepository.placeOrder(orderRequest,
                    onSuccess = {
                        Toast.makeText(this, "Orden realizada", Toast.LENGTH_SHORT).show()
                        CartManager.clearCart()
                        finish()
                    },
                    onError = { errorMessage ->
                        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvCart.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(CartManager.getCartItems()){
            calculeTotal()
        }
        binding.rvCart.adapter = adapter
    }

    private fun calculeTotal() {
        val total = CartManager.getCartItems().sumOf{ it.first.price * it.second }
        binding.txtTotal.text = "Total: $total Bs."
    }


}