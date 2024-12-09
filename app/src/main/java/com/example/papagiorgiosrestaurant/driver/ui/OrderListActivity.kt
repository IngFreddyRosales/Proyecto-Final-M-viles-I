package com.example.papagiorgiosrestaurant.driver.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.databinding.ActivityOrderListBinding
import com.example.papagiorgiosrestaurant.driver.ui.adapters.OrderListAdapter
import com.example.papagiorgiosrestaurant.driver.viewModels.OrderListViewModel
import com.example.papagiorgiosrestaurant.extras.SpaceItemDecoration

class OrderListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderListBinding
    private val orderListViewModel: OrderListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOrderListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModelObservers()
    }

    private fun setupRecyclerView() {
        binding.rvOrderList.layoutManager = LinearLayoutManager(this)
        binding.rvOrderList.addItemDecoration(SpaceItemDecoration(70))

    }

    private fun setupViewModelObservers() {
        orderListViewModel.orderResponses.observe(this) { orderResponse ->
            orderListViewModel.restaurants.observe(this){ restaurantList ->
                val adapter = OrderListAdapter(orderResponse, restaurantList)
                binding.rvOrderList.adapter = adapter

                adapter.setOnItemClickListener { order  ->
                    val intent = Intent(this, ConfirmedOrderActivity::class.java)
                    intent.putExtra("order_id", order.id)

                    startActivity(intent)
                    Toast.makeText(this, "Orden seleccionada: ${order.id}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        orderListViewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, "Error al obtener los pedidos: $errorMessage", Toast.LENGTH_SHORT).show()
        }
        orderListViewModel.fetchOrders()
    }
}