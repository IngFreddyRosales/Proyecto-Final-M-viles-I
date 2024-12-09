package com.example.papagiorgiosrestaurant.client.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.managers.CartManager
import com.example.papagiorgiosrestaurant.common.models.Product
import com.example.papagiorgiosrestaurant.databinding.ProductComponentBinding


class MenuListAdapter(
    private val menuList: List<Product>
): RecyclerView.Adapter<MenuListAdapter.MenuListViewHolder>() {

    private val quantities = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_component, parent, false)
        return MenuListViewHolder(view)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: MenuListViewHolder, position: Int) {
        holder.bind(menuList[position])
        //quantities.add(holder.quantityProduct)
    }

    fun getCuantities(): List<Int> {
        return quantities
    }

    class MenuListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ProductComponentBinding.bind(itemView)
        private var quantity = 0

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.txtDescription.text = product.description
            binding.productPrice.text = "$${product.price} Bs."

            Glide.with(itemView.context)
                .load(product.image)
                .placeholder(R.drawable.search_view_background)
                .into(binding.productImage)

            binding.lblTotal.text = quantity.toString()

            updateWithCart(product)


            binding.btnPlus.setOnClickListener(){
                quantity++
                binding.lblTotal.text = quantity.toString()
                CartManager.addProduct(product, 1)

            }

            binding.btnMinus.setOnClickListener(){
                if (quantity > 0) {
                    quantity--
                    binding.lblTotal.text = quantity.toString()
                    CartManager.updateQuantity(product, quantity)
                }
            }
        }

        fun updateWithCart(product: Product ){
            val cartItems = CartManager.getCartItems()
            val product = cartItems.find { it.first.id == product.id }
            if (product != null){
                quantity = product.second
                binding.lblTotal.text = quantity.toString()
            }
        }


    }
}
