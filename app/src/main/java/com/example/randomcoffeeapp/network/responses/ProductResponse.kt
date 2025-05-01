package com.example.randomcoffeeapp.network.responses

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val data: List<Product>
)

data class CreateOrderResponse(
    val orderId: String,
    val success: Boolean,
)

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val category: Category?,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    val prices: List<Price>
    )

data class Category(
    val id: Int,
    val slug: String,
)

data class Price(
    val value: Double,
    val currency: String,
    )

data class Order(
    val positions: Map<String, Int>,
    val token: String,
)
