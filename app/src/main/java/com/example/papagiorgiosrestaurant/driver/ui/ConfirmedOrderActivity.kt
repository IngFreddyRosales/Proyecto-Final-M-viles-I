package com.example.papagiorgiosrestaurant.driver.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.databinding.ActivityConfirmedOrderBinding
import com.example.papagiorgiosrestaurant.driver.viewModels.ConfirmedOrderViewModel

class ConfirmedOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmedOrderBinding
    private lateinit var viewModel: ConfirmedOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityConfirmedOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(ConfirmedOrderViewModel::class.java)

        val orderId = intent.getIntExtra("order_id", -1)
        if (orderId == -1) {
            Toast.makeText(this, "Order ID: $orderId", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupViewModelObservers()
        viewModel.fetchOrderDetails(orderId)
        binding.btnAccept.setOnClickListener {
            viewModel.acceptOrder(orderId)
            finish()
        }
    }

    private fun setupViewModelObservers() {
        viewModel.orderDetails.observe(this) { orderDetails ->
            binding.lblOrderId.text = "Numero de orden: ${orderDetails.id}"
            binding.lblTotalOrder.text = "Total: ${orderDetails.total}"
        }

        viewModel.restaurant.observe(this) { restaurant ->
            binding.lblRestaurantName.text = "Restaurant: ${restaurant.name}"
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }


    }
}