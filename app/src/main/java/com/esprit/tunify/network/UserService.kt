package com.esprit.tunify.network

import com.esprit.tunify.model.User
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    data class UserResponse(
        @SerializedName("user")
        val user: User
    )

    data class LoginBody(val email: String, val password: String)
    data class UserBody(val name: String, val lastname: String, val email: String, val password: String, val adress: String, val phone: String)


    @POST("api/user/login")
    fun login(@Body loginBody: LoginBody): Call<UserResponse>

    @POST("api/user/signup")
    fun register(@Body userBody: UserBody): Call<UserResponse>

}