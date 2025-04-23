package com.example.randomcoffeeapp.network

import com.example.randomcoffeeapp.network.responses.GetProductResponse
import com.example.randomcoffeeapp.network.responses.Product
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeShopApi {
    @GET("api/v1/products")
    suspend fun getProducts() : GetProductResponse // получение всех товаров

    @GET("api/v1/products/products{id}")
    suspend fun getProduct(@Path("id") id: Int) : Product // получение товара по id

}

// клиент
object CoffeeApi {
    private const val BASE_URL = "https://coffeeshop.academy.effective.band/api/"
    val coffeeShopApi: CoffeeShopApi by lazy {
        val contentType = "application/json".toMediaType()
        val json = Json {ignoreUnknownKeys = true}
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BASE_URL)
            .build()
            .create(CoffeeShopApi::class.java)
    }
}



