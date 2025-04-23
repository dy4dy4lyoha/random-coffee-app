package com.example.randomcoffeeapp.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class ReadProductsResponse(
    val name: String?,
    val description: String?,
    val imageUrl: String?,

    var page: Int?,
    var size: Int?,
    var count: Int?,

    val id: Int?,
    val slug: String?,

    val value: String,
    val currency: String,
)
