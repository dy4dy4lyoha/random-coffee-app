
package com.example.randomcoffeeapp.ui.presenation.screens.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.presenation.models.ProductViewModel
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.randomcoffeeapp.ui.presenation.models.ProductState
import coil.compose.AsyncImage
import java.io.IOException

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    id: Int,
    ) {

    val productViewModel: ProductViewModel = viewModel()
    val productState by productViewModel.productState.collectAsState()

    LaunchedEffect(key1 = id) {
        productViewModel.getProduct(id)
    }

    // карточка товара
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 240.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            // передаем изображение
            when (productState) {
                is ProductState.Success -> {
                    AsyncImage(
                        model = (productState as ProductState.Success).product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(top = 8.dp)
                    )
                }
                else -> {
                    Spacer (modifier = Modifier.size(100.dp))
                }
            }
            // передаем название товара
            when (productState) {
                is ProductState.Loading -> { CircularProgressIndicator() }
                is ProductState.Error ->
                    Text (
                        text = "Error",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                is ProductState.Success -> {
                    Text(
                        text = (productState as ProductState.Success).product.name ?: "Unknown",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Row (
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "245p",
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.width(64.dp))
                // кнопка добавления товара в корзину
                Button(
                    onClick = onClick,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF269DD1)),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductCardPreview() {
    RandomCoffeeAppTheme {
        ProductCard(onClick = {}, id = 1)
    }
}