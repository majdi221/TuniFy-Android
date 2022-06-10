package com.esprit.tunify.model

data class CartItem (
    var product: Product,
    var quantity: Int=0,
    var totalPrice: Double=0.00,
)

