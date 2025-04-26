package com.example.randomcoffeeapp.network.responses

import com.google.gson.annotations.SerializedName

data class GetProductResponse(
    val products: List<Product>
)

data class Product(
    val id: Int,
    val name: String,
    val description: String? = null,
    val category: Category?,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    val prices: List<Price>
    )

data class Category(
    val id: Int,
    val slug: String,
)

data class Price(
    val value: Int,
    val currency: String,
)
