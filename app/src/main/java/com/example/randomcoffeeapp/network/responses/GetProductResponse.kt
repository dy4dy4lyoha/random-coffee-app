package com.example.randomcoffeeapp.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProductResponse(
    val products: List<Product>
)

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val description: String? = null,
    val category: Category,
    @SerialName("imageUrl")
    val imageUrl: String?,
    val prices: List<Price>
    )

@Serializable
data class Category(
    val id: Int,
    val slug: String,
)

@Serializable
data class Price(
    val value: String,
    val currency: String,
)
