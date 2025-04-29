package com.example.randomcoffeeapp.network

import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.network.responses.ProductResponse
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeeShopApi {
    @GET("v1/products")
    suspend fun getProducts() : ProductResponse // getting the all products

    @GET("v1/products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product // getting the only one product by id

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



