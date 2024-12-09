// RestaurantListActivity.kt

package com.example.papagiorgiosrestaurant.client.ui

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.adapters.RestaurantAdapter
import com.example.papagiorgiosrestaurant.client.viewModels.RestaurantViewModel
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.databinding.ActivityRestaurantListBinding
import com.example.papagiorgiosrestaurant.extras.SpaceItemDecoration

class RestaurantListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantListBinding
    private val restaurantViewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupSearchView()
        setupViewModelObservers()
        setupLogOutButton()

        binding.btnCart.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
    }



    private fun setupRecyclerView() {
        binding.rvRestaurants.layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.addItemDecoration(SpaceItemDecoration(70))
    }

    private fun setupSearchView() {
        binding.svRestaurant.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.rvRestaurants.let {
                    (it.adapter as RestaurantAdapter).filterRestaurant(newText.toString())
                }
                return true
            }
        })
    }

    private fun setupViewModelObservers() {
        restaurantViewModel.restaurant.observe(this) { restaurantList ->
            val adapter = RestaurantAdapter(restaurantList)
            binding.rvRestaurants.adapter = adapter

            adapter.setOnItemClickListener { restaurant -> //
                navigateToMenuList(restaurant)
            }
        }

        restaurantViewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }

        restaurantViewModel.getRestaurants()
    }

    private fun setupLogOutButton() {
        binding.btnLogOut.setOnClickListener {
            MainActivity.access_token = ""
            finish()
        }
    }

    private fun navigateToMenuList(restaurant: Restaurant) {
        val intent = Intent(this, MenuListActivity::class.java)
        intent.putExtra("restaurant_id", restaurant.id)
        intent.putExtra("restaurant_name", restaurant.name)
        intent.putExtra("restaurant_image", restaurant.logo) // Ensure the key is "restaurant_image"
        startActivity(intent)
    }
}