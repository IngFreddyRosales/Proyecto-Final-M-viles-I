package com.example.papagiorgiosrestaurant.client.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.managers.CartManager
import com.example.papagiorgiosrestaurant.common.models.Product
import com.example.papagiorgiosrestaurant.databinding.ItemOrderBinding

class CartAdapter(
    private val cartItems: List<Pair<Product, Int>>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOrderBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: Pair<Product, Int>) {
            val product = cartItem.first
            var quantity = cartItem.second

            binding.productName.text = product.name
            binding.productPrice.text = "$${product.price} Bs."
            binding.lblQuantity.text = quantity.toString()

            binding.buttonnPlus.setOnClickListener {
                quantity++
                binding.lblQuantity.text = quantity.toString()
                CartManager.updateQuantity(product, quantity)
                onQuantityChanged()
            }

            binding.buttonnMinus.setOnClickListener {
                if (quantity > 0) {
                    quantity--
                    binding.lblQuantity.text = quantity.toString()
                    CartManager.updateQuantity(product, quantity)
                    onQuantityChanged()
                }
            }

            binding.btnRemove.setOnClickListener {
                CartManager.removeProduct(product)
                notifyDataSetChanged()
                onQuantityChanged()
            }
        }
    }
}


