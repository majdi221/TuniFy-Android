package com.esprit.tunify.network

import com.esprit.tunify.model.items
import com.esprit.tunify.model.Order
import com.esprit.tunify.model.User
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface OrderService {
    data class OrderResponse(
        @SerializedName("order")
        val order: Order
    )


    data class OrderBody (
        var user: User,
        var items: MutableList<items>,
        var totalCost: Double,
        var address: String,
        var createdAt: Date
    )
    @GET("api/order/myorders")
    fun getOrders(@Body user: User): Call<Order>

    @Headers("Content-Type: application/json")
    @POST("api/order/addorder")
    fun addOrder(@Body order:OrderBody ): Call<Order>


}