package com.esprit.tunify

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.LoginActivity.Constants.SHARED_PREF_SESSION
import com.esprit.tunify.adapter.CartAdapter
import com.esprit.tunify.model.*
import com.esprit.tunify.network.ApiService
import com.esprit.tunify.network.OrderService
import com.esprit.tunify.network.UserService
import com.google.gson.Gson
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CartFragment : Fragment() {
    object Constants {
        const val SHARED_PREF_SESSION = "SESSION"
    }
    //private var items :Collection<CartItem> = ArrayList<CartItem>()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        val cB = view.findViewById<Button>(R.id.checkoutButton)
        cB.setOnClickListener {
            val cart = getCart()
            val items: MutableList<items> = arrayListOf()
            val order : Order =Order("",User("","","","","","",""), items,0.0,"",Date())
            var total=0.0
            val sp : SharedPreferences = requireActivity().getSharedPreferences(Constants.SHARED_PREF_SESSION,MODE_PRIVATE)
            val user = sp.getString("USER_DATA",null)
            val sessionUser: User = Gson().fromJson(user, User::class.java)
            Log.d("User", "=" +sessionUser)


            cart.forEachIndexed { index, cartItem ->

                val item: items = items("",0, 0.0,0.0)

                    item.productId = cartItem.product._id
                    item.price = cartItem.product.price
                    item.total = cartItem.quantity * cartItem.product.price
                    item.quantity = cartItem.quantity
                    items.add(item)
                    Log.d("success", "item"+item)
                    Log.d("success", "cartitem"+cartItem)

                    total=total+item.total



        }
            Log.d("success", "listitem"+items)
           /* order.items=items
            order.address="address"
            order.totalCost=total
            order.createdAt=Date()
            order.user=sessionUser*/
            val orderr = OrderService.OrderBody(sessionUser,items,total,"address",Date())
            Log.d("Order", "= "+order)
            addOrder(orderr)

        }

        initAdapter(view)
        return view
    }
private  fun addOrder(order:OrderService.OrderBody){

    Log.d("From Fun", "Order= "+order)
    ApiService.orderService.addOrder(order).enqueue(
        object : Callback<Order> {
            override fun onResponse(
                call: Call<Order>,
                response: Response<Order>
            ) {
                if (response.code() == 200) {
                    Log.d("success", "order added")

                    Log.d("Result", "=: "+response.body())
                }
                else {
                    Log.d("Error", ":"+ response.code())
                    Log.d("Result", "=: "+call.toString())
                }

            }

            override fun onFailure(
                call: Call<Order>,
                t: Throwable
            ) {
                Log.d("FAIL", "fail")
            }


        }
    )
}
    private fun initAdapter(view : View) {
        val cRecyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        cartAdapter = CartAdapter(requireActivity().applicationContext, getCart())
        cartAdapter.notifyDataSetChanged()

        cRecyclerView.adapter = cartAdapter

        cRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

    }


    companion object {

        fun addItem(cartItem: CartItem) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.product._id == cartItem.product._id }
            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {
                targetItem.quantity++
            }
            saveCart(cart)
        }

        fun removeItem(cartItem: CartItem, context: Context) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.product._id == cartItem.product._id }
            if (targetItem != null)
            {
                cart.remove(targetItem)
            }

            saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())!!
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
                getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }


        @JvmStatic
        fun newInstance() =
            CartFragment()

    }
}