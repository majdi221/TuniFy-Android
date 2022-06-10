package com.esprit.tunify.network

import com.esprit.tunify.model.Product
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ProductService {

    data class ProductResponse(
        @SerializedName("product")
        val product: Product
    )

    data class OneProductBody(val _id: String)

    @GET("api/product/one")
    fun login(@Body oneProductBody: OneProductBody): Call<ProductResponse>

}