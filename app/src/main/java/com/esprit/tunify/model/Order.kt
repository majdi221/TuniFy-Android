package com.esprit.tunify.model

import java.util.*

data class Order (
    var _id: String,
    var user: String,
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