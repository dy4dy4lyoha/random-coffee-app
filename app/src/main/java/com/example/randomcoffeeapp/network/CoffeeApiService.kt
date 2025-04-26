package com.example.randomcoffeeapp.network

import com.example.randomcoffeeapp.network.responses.GetProductResponse
import com.example.randomcoffeeapp.network.responses.Product
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeShopApi {
    @GET("v1/products")
    suspend fun getProducts() : GetProductResponse // getting the all products

    @GET("v1/products/product/{id}")
    suspend fun getProduct(@Path("id") id: Int) : Product // getting the product by ID

}

// Client
object CoffeeApi {
    private const val BASE_URL = "https://coffeeshop.academy.effective.band/api/"

    val coffeeShopApi: CoffeeShopApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        // Json parser
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
            explicitNulls = false
        }
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CoffeeShopApi::class.java)
    }
}



