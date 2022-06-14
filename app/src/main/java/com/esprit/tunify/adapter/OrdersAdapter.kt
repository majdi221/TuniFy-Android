package com.esprit.tunify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.R
import com.esprit.tunify.model.Order
import kotlinx.android.synthetic.main.order_row.view.*

class OrdersAdapter (var context: Context, var orders: List<Order>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.bindItem(orders[position])
    }

    override fun getItemCount(): Int = orders.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(order: Order) {

            // This displays the cart item information for each item
            itemView.orderId.text = order._id
            itemView.totalAmount.text = order.totalCost.toString()
            itemView.orderDate.text = order.createdAt.toString()
            itemView.orderAddress.text = order.address

        }
    }

}