package com.esprit.tunify.network

import com.esprit.tunify.model.Product
import retrofit2.http.GET

interface RetroService {

    @GET("api/product/all")
    suspend fun getAllProducts():Collection<Product>


}