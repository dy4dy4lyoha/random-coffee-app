package com.example.randomcoffeeapp.ui.presenation.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcoffeeapp.network.CoffeeApi
import com.example.randomcoffeeapp.network.responses.Product
import kotlinx.coroutines.launch
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



sealed class ProductState {
    data class Success(val product: Product) : ProductState()
//    data class SuccessList(val product: List<Product> ) : ProductState()
    object Error : ProductState()
    object Loading : ProductState()
}

class ProductViewModel() : ViewModel() {
    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
    val productState: StateFlow<ProductState> = _productState

    // получение 1 продукта по id
    fun getProduct(id: Int) {
        viewModelScope.launch {
            _productState.value = ProductState.Loading
            try {
                val product = CoffeeApi.coffeeShopApi.getProduct(id)
                _productState.value = ProductState.Success(product)
            } catch (e: IOException) {
                _productState.value = ProductState.Error
            }
        }
    }

//    // получение всех продуктов
//    fun getProducts() {
//        viewModelScope.launch {
//            _productState.value = ProductState.Loading
//            try {
//                val productsResponse = CoffeeApi.coffeeShopApi.getProducts()
//                _productState.value = ProductState.SuccessList(productsResponse.products)
//            } catch (e: IOException) {
//                _productState.value = ProductState.Error
//            }
//        }
//    }
}