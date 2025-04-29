package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomcoffeeapp.ui.presenation.models.AllProductsState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.presenation.screens.components.BasketButton
import com.example.randomcoffeeapp.ui.presenation.screens.components.ProductCard
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme


@Composable
fun HomeScreen(allProductsViewModel: ProductViewModel = viewModel()) {

    val allProductsState by allProductsViewModel.allProductsState.collectAsState()

//    ProductCard(
//        product = Product(
//            1,
//            "type",
//            "zz",
//            category = Category(1, "z"),
//            prices = listOf(Price(255.5, "$"))
//        )
//    )

    Column {
        /// header
        /// grid

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
                    items(products) { products ->
                        ProductCard(product = products)
                    }
                }
            }
            is AllProductsState.Error -> {
                Text(text = "Error")
            }
        }


        Row(modifier = Modifier.align(Alignment.End)) {
            BasketButton()
        }
    }
    BasketBottomSheet()
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

//    ProductCard(
//        product = Product(
//            1,
//            "type",
//            "zz",
//            category = Category(1, "z"),
//            prices = listOf(Price(255.5, "$"))
//        )
//    )