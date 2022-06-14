package com.esprit.tunify

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esprit.tunify.adapter.OrdersAdapter
import com.esprit.tunify.model.Order
import com.esprit.tunify.model.User
import com.esprit.tunify.network.ApiService
import com.esprit.tunify.network.OrderService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderFragment : Fragment() {

    private lateinit var ordersAdapter : OrdersAdapter

    private var orders : MutableList<Order> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_order, container, false)

        val sp: SharedPreferences = requireActivity().getSharedPreferences(CartFragment.Constants.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        val user = sp.getString("USER_DATA", null)
        val sessionUser: User? = Gson().fromJson(user, User::class.java)



        ApiService.orderService.getOrders(OrderService.OrdersBody(sessionUser!!._id)).enqueue(
            object : Callback<OrderService.OrderResponse> {

                override fun onResponse(
                    call: Call<OrderService.OrderResponse>,
                    response: Response<OrderService.OrderResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("success", "orders printed " + response.body())
                        orders = response.body()?.order as MutableList<Order>
                        println(response.body())
                    } else {
                        Log.d("response", "error code is = " + response.code())
                    }

                }

                override fun onFailure(
                    call: Call<OrderService.OrderResponse>,
                    t: Throwable
                ) {
                    t.printStackTrace()
                }
            }
        )



        initAdapter(view)
        return view
    }

    private fun initAdapter(view: View) {

        val oRecyclerView = view.findViewById<RecyclerView>(R.id.orderRecyclerView)
        oRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        ordersAdapter = OrdersAdapter(requireContext(), orders)
        oRecyclerView.adapter = ordersAdapter

        //ordersAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            OrderFragment()
    }
}