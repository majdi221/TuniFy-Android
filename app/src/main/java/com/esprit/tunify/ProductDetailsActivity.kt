package com.esprit.tunify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {

    private var product_name : TextView? = null
    private var product_price_detail : TextView? = null
    private var photo : ImageView? = null
    //private var addItem : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Product Details"

        product_name = findViewById(R.id.product_name)
        product_price_detail = findViewById(R.id.product_price_detail)
        photo = findViewById(R.id.photo)
        //addItem = findViewById(R.id.addItem)

        val image = intent.getStringExtra("image")
        val price = intent.getStringExtra("price").toString()
        val name = intent.getStringExtra("name")
        product_name!!.text = name
        product_price_detail!!.text = price
        Picasso.get().load(image).into(photo)

        //addItem!!.setOnClickListener {  }

    }
}