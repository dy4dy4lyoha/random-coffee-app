package com.example.randomcoffeeapp.ui.presenation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.presenation.models.OrderCreationState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.theme.openSansFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope,
    productMap: MutableState<MutableMap<Product, Int>>,
    onRemoveFromBasket: (Product) -> Unit,
    productViewModel: ProductViewModel = viewModel()
) {
    val totalPrice = remember(productMap.value) {
        productMap.value.entries.sumOf { (product, count) ->
            product.prices[0].value * count
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val orderCreationState by productViewModel.orderCreationState.collectAsState()

    // main column
    Column {
        // row with title of basket
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.small_padding))
        ) {
            Text(
                text = stringResource(R.string.your_order),
                fontSize = 30.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = openSansFamily,
            )
            IconButton(
                onClick = {
                    productMap.value = mutableMapOf()
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_delete_all),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.medium_icon))
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())

        productMap.value.forEach { (product, count) ->
            // rows with image, name, count and price of products in basket
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium_padding))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // image of product
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.small_image))
                    )
                    // name of product
                    Text(
                        text = product.name,
                        fontSize = 22.sp,
                        fontFamily = openSansFamily,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.medium_padding))
                    )
                    // count of product
                    Text(
                        text = count.toString(),
                        fontSize = 22.sp,
                        fontFamily = openSansFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.medium_padding))
                    )
                }

                // price of product
                Text(
                    text = product.prices[0].value.toString(),
                    fontSize = 22.sp,
                    fontFamily = openSansFamily,
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.medium_padding))
        ) {
            Text(
                text = stringResource(R.string.total_price),
                fontSize = 22.sp,
                fontFamily = openSansFamily,
            )
            Text(
                text = "${totalPrice.toInt()}₽",
                fontSize = 22.sp,
                fontFamily = openSansFamily,
            )
        }
        // button create order
        Button(
            onClick = {
                val positions = mapOf("1" to 2, "3" to 4)
                val token = "<FCM Registration Token>"
                productViewModel.createOrder(positions, token)
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.primary_button)),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.large_padding))
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.create_order)
            )
        }
        Box {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
        when (val state = orderCreationState) {
            OrderCreationState.Idle -> {}
            OrderCreationState.Loading -> {}
            is OrderCreationState.Success -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Заказ успешно создан"
                    )
                }
            }
            is OrderCreationState.Error -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = "Возникла ошибка при заказе"
                    )
                }
            }
        }
    }
}
