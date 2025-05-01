package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Category
import com.example.randomcoffeeapp.network.responses.Price
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
import com.example.randomcoffeeapp.ui.theme.openSansFamily

@Composable
fun ProductDescriptionScreen(
    product: Product,
    onBackClick: () -> Unit
) {
    // main column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 32.dp, end = 32.dp)
    ) {
        // row with icon back to previous screen
        Row (
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon (
                    painter = painterResource(R.drawable.ic_back_to_previous),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        // box with image
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
        }
        // title
        Text(
            text = product.name,
            fontSize = 40.sp,
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Bold
        )
        // description
        Text(
            text = product.description,
            fontSize = 16.sp,
            fontFamily = openSansFamily,
        )
    }
}

@Preview
@Composable
fun ProductDescriptionPreview() {
    RandomCoffeeAppTheme {
        ProductDescriptionScreen (
            onBackClick = {},
            product = Product(
                1,
                "type",
                "zz",
                category = Category(1, "z"),
                prices = listOf(Price(255.5, "$"))
            )
        )
    }
}