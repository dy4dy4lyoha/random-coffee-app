package com.example.randomcoffeeapp.ui.presenation.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomcoffeeapp.network.responses.Category
import com.example.randomcoffeeapp.network.responses.Price
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.presenation.models.AllProductsState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.presenation.screens.components.ProductCard
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme

@Composable
fun HomeScreen(allProductsViewModel: ProductViewModel = viewModel()) {

    val allProductsState by allProductsViewModel.allProductsState.collectAsState()

    ProductCard(
        product = Product(
            1,
            "type",
            "zz",
            category = Category(1, "z"),
            prices = listOf(Price(255.5, "$"))
        )
    )

    when (allProductsState) {
        is AllProductsState.Loading -> {
            CircularProgressIndicator()
        }
        is AllProductsState.Success -> {
            val products = (allProductsState as AllProductsState.Success).products
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(products) { product ->
                    ProductCard(product = product)
                }
            }
        }
        is AllProductsState.Error -> {
            Text(text = "Error")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RandomCoffeeAppTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            HomeScreen()
        }
    }
}