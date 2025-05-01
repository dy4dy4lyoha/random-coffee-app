package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.presenation.models.AllProductsState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.presenation.screens.components.ProductCard
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
import com.example.randomcoffeeapp.ui.theme.openSansFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(allProductsViewModel: ProductViewModel = viewModel()) {

    val allProductsState by allProductsViewModel.allProductsState.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var productMap: MutableState<MutableMap<Product, Int>> = remember { mutableStateOf(mutableMapOf()) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var selectedCategorySlug by remember { mutableStateOf<String?>(null) }

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

    // navigation on product
    val handleNavigation: (Product?) -> Unit = { product ->
        selectedProduct = product
    }

    // main column
    Box (modifier = Modifier.fillMaxSize()) {
        if (selectedProduct != null) {
            ProductDescriptionScreen(
                product = selectedProduct!!,
                onBackClick = { handleNavigation(null) }
            )
        } else {
            // column with scrollable cards
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box {
                    LazyRow (
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.small_padding))
                    ) {
                        val categories = if (allProductsState is AllProductsState.Success) {
                            (allProductsState as AllProductsState.Success).products.mapNotNull {it.category?.slug}.distinct()
                        } else {
                            emptyList()
                        }
                        items (categories.size) {index ->
                            val categorySlug = categories[index]
                            Button(
                                onClick = {
                                    if (selectedCategorySlug == categorySlug) {
                                        selectedCategorySlug = null

                                    } else {
                                        selectedCategorySlug = categorySlug
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedCategorySlug == categorySlug) {
                                        colorResource(R.color.primary_button)
                                    } else {
                                        colorResource(R.color.white)
                                    }
                                ),
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.small_padding))
                            ) {
                                Text (
                                    text = categorySlug,
                                    color = if (selectedCategorySlug == categorySlug) {
                                        colorResource(R.color.white)
                                    } else {
                                        colorResource(R.color.neutral_icon)
                                    }
                                )
                            }
                        }
                    }
                }
                // column with product cards
                LazyColumn {
                    if (allProductsState is AllProductsState.Success) {
                        val productsToShow = if (selectedCategorySlug == null) {
                            (allProductsState as AllProductsState.Success).products
                        } else {
                            (allProductsState as AllProductsState.Success).products.filter {
                                it.category?.slug == selectedCategorySlug
                            }
                        }

                        val groupedProducts: Map<String, List<Product>> = productsToShow.groupBy {
                            it.category?.slug ?: "None"
                        }
                        groupedProducts.forEach { (categorySlug, productsInCategory) ->
                            item {
                                Text (
                                    text = categorySlug,
                                    fontSize = 36.sp,
                                    fontFamily = openSansFamily,
                                    modifier = Modifier
                                        .padding(
                                            start = dimensionResource(R.dimen.small_padding),
                                            top = dimensionResource(R.dimen.large_padding),
                                            bottom = dimensionResource(R.dimen.large_padding)

                                        )
                                )
                            }
                            item {
                                FlowRow (
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    val productsToDisplay  = if (selectedCategorySlug == null || categorySlug == selectedCategorySlug) {
                                        productsInCategory
                                    } else {
                                        emptyList()
                                    }
                                    productsToDisplay.forEach { product ->
                                        ProductCard(
                                            product = product,
                                            productMap = productMap,
                                            onAddToBasket = addToBasket,
                                            onNavigate = {
                                                selectedProduct = it
                                                handleNavigation
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    } else if (allProductsState is AllProductsState.Loading) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(dimensionResource(R.dimen.large_icon))
                            )
                        }
                    } else if (allProductsState is AllProductsState.Error) {
                        item {
                            Text(text = stringResource(R.string.error))
                        }
                    }
                }
            }
            // basket button
            // idk why the button does not work through import, so I did it here
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
                    contentPadding = PaddingValues(dimensionResource(R.dimen.small_padding)),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_button)),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.small_corner_shape)),
                    modifier = Modifier
                        .size(
                            width = dimensionResource(R.dimen.width_basket_button),
                            height = dimensionResource(R.dimen.height_basket_button),
                        )
                        .align(Alignment.BottomEnd)
                        .padding(dimensionResource(R.dimen.small_padding))
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = null,
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.small_icon))
                        )
                        Text(
                            text = "${totalPrice.toInt()}₽"
                        )
                    }
                }
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

