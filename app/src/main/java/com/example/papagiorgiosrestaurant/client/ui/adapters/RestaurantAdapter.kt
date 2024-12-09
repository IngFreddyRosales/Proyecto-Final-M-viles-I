package com.example.papagiorgiosrestaurant.client.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.common.models.Restaurant
import com.example.papagiorgiosrestaurant.databinding.RestaurantComponentBinding

class RestaurantAdapter(
    private val restaurant: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var filteredRestaurant = restaurant
    private var onItemClickListener: ((Restaurant) -> Unit)? = null

    fun setOnItemClickListener(listener: (Restaurant) -> Unit) {
        onItemClickListener = listener
    }

    fun filterRestaurant(query: String) {
        filteredRestaurant = restaurant.filter {
            it.name.contains(query, ignoreCase = true) || it.address.contains(query, ignoreCase = true)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_component, parent, false)
        return RestaurantViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(filteredRestaurant[position])
    }

    override fun getItemCount(): Int = filteredRestaurant.size

    class RestaurantViewHolder(
        itemView: View,
        private val onItemClickListener: ((Restaurant) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = RestaurantComponentBinding.bind(itemView)

        fun bind(restaurant: Restaurant) {
            binding.titleRestaurant.text = restaurant.name
            binding.addressRestaurant.text = restaurant.address
            Glide.with(itemView.context)
                .load(restaurant.logo)
                .placeholder(R.drawable.baseline_restaurant_24)
                .into(binding.logoRestaurant)



            itemView.setOnClickListener {
                onItemClickListener?.invoke(restaurant)
            }
        }
    }
}