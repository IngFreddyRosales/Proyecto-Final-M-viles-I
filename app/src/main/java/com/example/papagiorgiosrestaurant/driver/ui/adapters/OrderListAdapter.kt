package com.example.papagiorgiosrestaurant.driver.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.common.models.OrderResponse
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.databinding.PendingOrderItemBinding

class OrderListAdapter(
    private val orders: List<OrderResponse>,
    private val restaurants: List<Restaurant>
) : RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    private var onItemClickListener: ((OrderResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrderResponse) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pending_order_item, parent, false)
        return OrderViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position], restaurants)
    }

    class OrderViewHolder(
        itemView: View,
        private val onItemClickListener: ((OrderResponse) -> Unit)?
        ) : RecyclerView.ViewHolder(itemView) {
        private val binding = PendingOrderItemBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderResponse, restaurants: List<Restaurant>) {
            binding.idOrder.text = "Orden nro: ${order.id}"
            if (order.order_details.isNotEmpty()) {
                val restaurant = restaurants.find { it.id == order.restaurant_id }
                binding.RestaurantName.text = restaurant?.name

                Glide.with(itemView.context)
                    .load(restaurant?.logo)
                    .placeholder(R.drawable.baseline_restaurant_24)
                    .into(binding.imageView)
            } else {
                binding.RestaurantName.text = "No Restaurant"
            }

            itemView.setOnClickListener {
                onItemClickListener?.invoke(order)
            }

        }


    }
}
