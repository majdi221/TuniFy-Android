package com.esprit.tunify.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val userService: UserService by lazy {
        retrofit().create(UserService::class.java)
    }

    val orderService: OrderService by lazy {
        retrofit().create(OrderService::class.java)
    }
    val productService: ProductService by lazy {
        retrofit().create(ProductService::class.java)
    }


}