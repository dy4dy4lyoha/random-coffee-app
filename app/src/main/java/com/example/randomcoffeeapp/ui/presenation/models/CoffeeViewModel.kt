package com.example.randomcoffeeapp.ui.presenation.models


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcoffeeapp.network.CoffeeApi
import com.example.randomcoffeeapp.network.responses.Product
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// class for getting the only one product by id
sealed class ProductState {
    data class Success(val product: Product) : ProductState()
    object Error : ProductState()
    object Loading : ProductState()
}

// class for getting the all products
sealed class AllProductsState {
    data class Success(val products: List<Product>) : AllProductsState()
    object Error : AllProductsState()
    object Loading : AllProductsState()
}

class ProductViewModel() : ViewModel() {
//    private val _productState = MutableStateFlow<ProductState>(ProductState.Loading)
//    val productState: StateFlow<ProductState> = _productState

    private val _allProductsState = MutableStateFlow<AllProductsState>(AllProductsState.Loading)
    val allProductsState: StateFlow<AllProductsState> = _allProductsState

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _allProductsState.value = AllProductsState.Loading
            try {
                val products = CoffeeApi.coffeeShopApi.getProducts()
                _allProductsState.value = AllProductsState.Success(products)
            } catch (e: Exception) {
                _allProductsState.value = AllProductsState.Error
            }
        }
    }
}

//    // getting the only one product by id
//    fun getProduct(productId: Int) {
//        viewModelScope.launch {
//            _productState.value = ProductState.Loading
//            try {
//                val product = CoffeeApi.coffeeShopApi.getProduct(productId)
//                _productState.value = ProductState.Success(product)
//            } catch (e: Exception) {
//                _productState.value = ProductState.Error
//            }
//        }
//    }