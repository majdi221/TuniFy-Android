package com.esprit.tunify.model

import java.util.*

data class Order (
    val _id: String,
    var user: User,
    var items: List<items>,
    var totalCost: Double,
    var address: String,
    var createdAt: Date
    )

data class items(
    var productId: String,
    var quantity: Int,
    var price: Double,
    var total: Double
    )