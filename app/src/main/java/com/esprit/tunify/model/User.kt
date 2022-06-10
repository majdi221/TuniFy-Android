package com.esprit.tunify.model

data class User(
    val _id: String,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String,
    val adress: String,
    val phone: String
)