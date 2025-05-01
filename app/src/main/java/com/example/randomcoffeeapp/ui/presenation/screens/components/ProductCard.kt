
package com.example.randomcoffeeapp.ui.presenation.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomcoffeeapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.network.responses.Price
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.theme.openSansFamily

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onAddToBasket: (Product) -> Unit,
    onDecreaseProduct: (Product) -> Unit,
    onIncreaseProduct: (Product) -> Unit,
    onNavigate: (Product?) -> Unit,
    productMap: MutableState<MutableMap<Product, Int>>,
    ) {

    val countOfProduct = productMap.value[product] ?: 0
    val showQuantityButtons = productMap.value.containsKey(product)

    // product card
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 240.dp)
            .clickable {
                onNavigate(product)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // image of product
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 8.dp)
            )
            // name of product
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = openSansFamily,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
            // row with text and buttons
            Row (
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                // button for add product in basket
                if (showQuantityButtons && countOfProduct > 0) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        QuantityButtons(
                            count = countOfProduct,
                            onIncrement = {
                                    if (countOfProduct < 10) {
                                        productMap.value = productMap.value.toMutableMap().apply {
                                        val currentCount = getOrDefault(product, 0)
                                            put(product, currentCount + 1)
                                    }
                                }
                            },
                            onDecrement = {
                                if (countOfProduct > 0) {
                                    productMap.value = productMap.value.toMutableMap().apply {
                                        val currentCount = getOrDefault(product, 0)
                                        if (currentCount > 1) {
                                            put(product, currentCount - 1)
                                        } else {
                                            remove(product)
                                        }
                                    }
                                }
                            },
                        )
                    }
                } else {
                    // showing price and formating his
                    if (product.prices.isNotEmpty()) {
                        Text(
                            text = if(product.prices[0].currency == "RUB") {
                                val amountInt = product.prices[0].value.toInt()
                                "$amountInt₽"
                            } else {
                                "${product.prices[0].value} ${product.prices[0].currency}"
                            },
                            fontSize = 22.sp,
                            fontFamily = openSansFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    AddToBasketButton (
                        onAdd = { onAddToBasket(product) }
                    )
                }
            }
        }
    }
}
