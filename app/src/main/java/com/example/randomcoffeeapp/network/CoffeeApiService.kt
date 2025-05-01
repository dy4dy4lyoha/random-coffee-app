package com.example.randomcoffeeapp.network

import com.example.randomcoffeeapp.network.responses.CreateOrderResponse
import com.example.randomcoffeeapp.network.responses.Order
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.network.responses.ProductResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CoffeeShopApi {
    @GET("v1/products")
    suspend fun getProducts() : ProductResponse // getting the all products

    @POST("v1/orders")
    suspend fun createOrder(@Body order: Order) : CreateOrderResponse // create order
}

// Client
object CoffeeApi {
    private const val BASE_URL = "https://coffeeshop.academy.effective.band/api/"

    val coffeeShopApi: CoffeeShopApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        // Json parser
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create()

        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CoffeeShopApi::class.java)
    }
}




