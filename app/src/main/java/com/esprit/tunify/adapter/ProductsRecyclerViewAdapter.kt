package com.esprit.tunify.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.CartFragment
import com.esprit.tunify.R
import com.esprit.tunify.model.CartItem
import com.esprit.tunify.model.Product
import com.squareup.picasso.Picasso

class ProductsRecyclerViewAdapter(private val onItemClick: (position: Int) -> Unit) :
    RecyclerView.Adapter<ProductsRecyclerViewAdapter.MyViewHolder>() {

    var items: Collection<Product> = ArrayList<Product>()


    fun setUpdatedData(items: Collection<Product>) {
        this.items = items
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener { _ ->
                onItemClick(absoluteAdapterPosition)
            }
        }

        val photo = view.findViewById<ImageView>(R.id.photo)
        val name = view.findViewById<TextView>(R.id.name)
        val price = view.findViewById<TextView>(R.id.price)

        fun bind(data: Product) {
            name.text = data.name
            price.text = data.price.toString()

            val url = data.image
            Log.d("image", "path = $url")
            Picasso.get().load(url).into(photo)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_row, parent, false)
        return MyViewHolder(view, onItemClick)
    }

    public fun position(): Int {
        return onItemClick.toString().toInt()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items.toList()[position])

        val button = holder.itemView.findViewById<Button>(R.id.addToCartButton)
        val product = items.toList()[position]


        button.setOnClickListener {

            val item = CartItem(product)

            CartFragment.addItem(item)

            Toast.makeText(holder.itemView.context, "product "+product.name+" added to cart", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}




