package com.example.randomcoffeeapp.ui.presenation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.presenation.models.OrderCreationState
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
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
                .padding(10.dp)
        ) {
            Text(
                text = "Ваш заказ",
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
                        .size(30.dp)
            ) }

        }
        Divider(modifier = Modifier.fillMaxWidth())

        productMap.value.forEach { (product, count) ->
            // rows with image, name, count and price of products in basket
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // image of product
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(55.dp)
                    )
                    // name of product
                    Text(
                        text = product.name,
                        fontSize = 22.sp,
                        fontFamily = openSansFamily,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    // count of product
                    Text(
                        text = count.toString(),
                        fontSize = 22.sp,
                        fontFamily = openSansFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
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
                .padding(10.dp)
        ) {
            Text(
                text = "Итого",
                fontSize = 22.sp,
                fontFamily = openSansFamily,
            )
            Text(
                text = totalPrice.toString(),
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF269DD1)
            ),
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = "Оформить заказ"
            )
        }
        Box() {
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

@Preview
@Composable
fun BasketBottomSheet() {
    RandomCoffeeAppTheme {
        BasketBottomSheet()
    }
}