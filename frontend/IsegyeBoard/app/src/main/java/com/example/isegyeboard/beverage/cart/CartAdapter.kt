package com.example.isegyeboard.beverage.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.isegyeboard.R

class CartAdapter : ListAdapter<CartClass, CartAdapter.CartViewHolder>(DiffCallback()) {

    private lateinit var cartUpdateListener: CartUpdateListener
    fun setCartListener(listener: CartUpdateListener) {
        this.cartUpdateListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.bind(cartItem)
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.cartName)
        private val quantityTextView: TextView = itemView.findViewById(R.id.cartQuantity)
        private val btnPlus: TextView = itemView.findViewById(R.id.plusButton)
        private val btnMinus: TextView = itemView.findViewById(R.id.minusButton)

        fun bind(cartItem: CartClass) {
            nameTextView.text = cartItem.name
            quantityTextView.text = "${cartItem.quantity}"

            btnPlus.setOnClickListener {
                // + 버튼 클릭 시 수량 증가
                increaseQuantity(cartItem)
            }

            btnMinus.setOnClickListener {
                // - 버튼 클릭 시 수량 감소
                decreaseQuantity(cartItem)
            }
        }
    }

    fun updateCartItems(newItems: List<CartClass>) {
        submitList(newItems)
        notifyDataSetChanged()
    }

    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        for (cartItem in currentList) {
            totalPrice += cartItem.price * cartItem.quantity
        }
        return totalPrice
    }

    private class DiffCallback : DiffUtil.ItemCallback<CartClass>() {
        override fun areItemsTheSame(oldItem: CartClass, newItem: CartClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartClass, newItem: CartClass): Boolean {
            return oldItem == newItem
        }
    }

    fun increaseQuantity(cartItem: CartClass) {
        val cart = CartManage.getInstance()

        cart.addItem(cartItem) // 장바구니에 음료 추가
        updateCartItems(cart.getItems())
        cartUpdateListener.onCartUpdated()
    }

    fun decreaseQuantity(cartItem: CartClass) {
        val cart = CartManage.getInstance()

        cart.reduceItem(cartItem)
        updateCartItems(cart.getItems())
        cartUpdateListener.onCartUpdated()
    }
}
