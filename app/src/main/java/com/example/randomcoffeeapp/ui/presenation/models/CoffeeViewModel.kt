package com.example.randomcoffeeapp.ui.presenation.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcoffeeapp.network.CoffeeApi
import com.example.randomcoffeeapp.network.responses.CreateOrderResponse
import com.example.randomcoffeeapp.network.responses.Order
import com.example.randomcoffeeapp.network.responses.Product
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import retrofit2.awaitResponse
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// class for getting the all products
sealed class AllProductsState {
    data class Success(val products: List<Product>) : AllProductsState()
    object Error : AllProductsState()
    object Loading : AllProductsState()
}

sealed class OrderCreationState {
    data class Success(val orderId: String) : OrderCreationState()
    data class Error(val message: String) : OrderCreationState()
    object Idle: OrderCreationState()
    object Loading: OrderCreationState()
}

class ProductViewModel() : ViewModel() {
    private val _allProductsState = MutableStateFlow<AllProductsState>(AllProductsState.Loading)
    val allProductsState: StateFlow<AllProductsState> = _allProductsState

    private val _orderCreationState = MutableStateFlow<OrderCreationState>(OrderCreationState.Idle)
    val orderCreationState: StateFlow<OrderCreationState> = _orderCreationState

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _allProductsState.value = AllProductsState.Loading
            try {
                val products = CoffeeApi.coffeeShopApi.getProducts()
                _allProductsState.value = AllProductsState.Success(products.data)

            } catch (e: Exception) {
                _allProductsState.value = AllProductsState.Error
            }
        }
    }

    fun createOrder(positions: Map<String, Int>, token: String) {
        viewModelScope.launch {
            _orderCreationState.value = OrderCreationState.Loading
            try {
                val orderId = sendCreateOrderRequest(positions, token)
                _orderCreationState.value = OrderCreationState.Success(orderId)
            } catch (e: Exception) {
                _orderCreationState.value = OrderCreationState.Error("Error")
            }
        }
    }

    suspend fun sendCreateOrderRequest(positions: Map<String, Int>, token: String): String {
        return try {
            val order = Order(positions = positions, token = token)
            val response = CoffeeApi.coffeeShopApi.createOrder(order)
            if (response.success) {
                response.orderId
            } else {
                throw Exception("Заказ не создан: ${response.toString()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}


