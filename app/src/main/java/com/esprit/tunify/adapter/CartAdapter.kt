package com.esprit.tunify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.CartFragment
import com.esprit.tunify.R
import com.esprit.tunify.model.CartItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_row.view.*

class CartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CartAdapter.ViewHolder {

        // The layout design used for each list item
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false)

        return ViewHolder(layout)
    }

    // This returns the size of the list.
    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: CartAdapter.ViewHolder, position: Int) {
        //we simply call the `bindItem` function here
        viewHolder.bindItem(cartItems[position])

        val button = viewHolder.itemView.findViewById<ImageButton>(R.id.deleteProductButton)
        val product = cartItems[position]


        button.setOnClickListener {
            val item = CartItem(product.product)
            CartFragment.removeItem(item, context)
            Toast.makeText(viewHolder.itemView.context, "product "+item.product.name+" deleted from cart", Toast.LENGTH_SHORT).show()
            notifyItemRemoved(viewHolder.absoluteAdapterPosition)


        }



    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {

            // This displays the cart item information for each item
            Picasso.get().load(cartItem.product.image).fit().into(itemView.productImageView)

            itemView.productNameTextView.text = cartItem.product.name

            itemView.productTotalPriceTextView.text = "${cartItem.product.price * cartItem.quantity}"

            itemView.quantitySpinner.text = cartItem.quantity.toString()

        }
    }

}