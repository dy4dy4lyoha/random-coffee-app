package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Category
import com.example.randomcoffeeapp.network.responses.Price
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.presenation.models.AllProductsState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.presenation.screens.components.ProductCard
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(allProductsViewModel: ProductViewModel = viewModel()) {

    val allProductsState by allProductsViewModel.allProductsState.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var productMap: MutableState<MutableMap<Product, Int>> = remember { mutableStateOf(mutableMapOf()) }

    // add product to basket
    val addToBasket:(Product) -> Unit = { product ->
        productMap.value = productMap.value.toMutableMap().apply {
            val currentCount = get(product) ?: 0
            put(product, currentCount + 1)
        }
    }

    // remove product (one product) from basket
    val removeFromBasket: (Product) -> Unit = { product ->
        productMap.value = productMap.value.toMutableMap().apply {
            val currentCount = get(product) ?: 0
            if (currentCount > 1) {
                put(product, currentCount - 1)
            } else {
                remove(product)
            }
        }
    }

    // calculating total price of products
    val totalPrice = remember(productMap.value) {
        productMap.value.entries.sumOf { (product, count) ->
            product.prices[0].value * count
        }
    }

    Box (modifier = Modifier.fillMaxSize()) {
        when (allProductsState) {
            is AllProductsState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            // grid from products
            is AllProductsState.Success -> {
                val products = (allProductsState as AllProductsState.Success).products
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(products) { products ->
                        ProductCard(
                            product = products,
                            productMap = productMap,
                            onAddToBasket = addToBasket,
                            onIncreaseProduct = addToBasket,
                            onDecreaseProduct = {},
                        )
                    }
                }
            }
            is AllProductsState.Error -> {
                Text(text = "Error")
            }
        }

        // basket button
        // idk why the button doesn`t work through import, so I did it here
        if (productMap.value.isNotEmpty()) {
            Button(
                onClick = {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        } else {
                            sheetState.show()
                        }
                    }
                },
                contentPadding = PaddingValues(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF269DD1)
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .size(width = 100.dp, height = 60.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cart),
                        contentDescription = null,
                    )
                    Text(
                        text = "${totalPrice.toInt()}₽"
                    )
                }
            }
        }
        // bottom sheet
        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                sheetState = sheetState
            ) {
                BasketBottomSheet(
                    sheetState = sheetState,
                    scope = scope,
                    productMap = productMap,
                    onRemoveFromBasket = removeFromBasket,
                )
            }
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

