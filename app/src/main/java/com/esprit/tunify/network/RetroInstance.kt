package com.esprit.tunify.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object{
        private const val BaseURL = "http://10.0.2.2:3000/"

        fun getRetroInstance():Retrofit{

            return Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }
}