package com.example.papagiorgiosrestaurant.client.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.adapters.CartAdapter
import com.example.papagiorgiosrestaurant.client.ui.adapters.MenuListAdapter
import com.example.papagiorgiosrestaurant.client.ui.managers.CartManager
import com.example.papagiorgiosrestaurant.client.viewModels.MenuListViewModel
import com.example.papagiorgiosrestaurant.databinding.ActivityMenuListBinding
import com.example.papagiorgiosrestaurant.extras.SpaceItemDecoration

class MenuListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuListBinding
    private val menuViewModel: MenuListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMenuListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val restaurantId = intent.getIntExtra("restaurant_id", -1)
        val restaurantName = intent.getStringExtra("restaurant_name")
        val restaurantImage = intent.getStringExtra("restaurant_image")

        if (restaurantId == -1) {
            Toast.makeText(this, "Invalid Restaurant ID: $restaurantId", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupRecyclerView()
        setupViewModelObservers()
        setRestaurantId(restaurantId)
        setupInfoRestaurant(restaurantName, restaurantImage)

        navigateToCartActivity()

        binding.btnViewCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

    }

    private fun setupRecyclerView() {
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
    }

    private fun setupInfoRestaurant(restauranName: String?, restaurantImage: String?) {

        binding.txtTitleRestaurant.text = restauranName
        Glide.with(this)
            .load(restaurantImage)
            .placeholder(R.drawable.baseline_restaurant_24)
            .into(binding.imageRestaurant)

    }

    private fun setupViewModelObservers() {
        menuViewModel.products.observe(this) { productList ->
            binding.rvProducts.adapter = MenuListAdapter(productList)
            binding.rvProducts.addItemDecoration(SpaceItemDecoration(70))

        }

        menuViewModel.error.observe(this) { errorMessage ->
            println("Error: $errorMessage")
        }
    }

    private fun setRestaurantId(restaurantId: Int) {
        menuViewModel.getMenu(restaurantId)
    }

    override fun onResume() { //vuelve a primer plano
        super.onResume()
        menuViewModel.products.observe(this) { productList ->
            binding.rvProducts.adapter = MenuListAdapter(productList)
        }
    }

    private fun navigateToCartActivity() {
        val intent = Intent(this, CartActivity::class.java)
        intent.putExtra("restaurant_id", intent.getIntExtra("restaurant_id", -1))
        intent.putExtra("restaurant_name", intent.getStringExtra("restaurant_name"))
    }
}