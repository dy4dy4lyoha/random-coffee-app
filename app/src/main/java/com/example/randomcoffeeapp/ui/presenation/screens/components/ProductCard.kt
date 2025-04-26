
package com.example.randomcoffeeapp.ui.presenation.screens.components

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.network.responses.Product

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    product: Product
    ) {

    var showQuantityButtons by remember { mutableStateOf(false) }
    var countOfProduct by remember { mutableStateOf(0) }


    // product card
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
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
            Row (
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            ) {
                if (product.prices.isNotEmpty()) {
                    Text(
                        text = "${product.prices[0].value} ${product.prices[0].currency}",
                        fontSize = 24.sp
                    )
                }

                Spacer(modifier = Modifier.width(64.dp))

                // button for add product in basket
                if (showQuantityButtons) {
                    QuantityButtons(
                        count = countOfProduct,
                        onIncrement = {countOfProduct++},
                        onDecrement = {if(countOfProduct > 0) countOfProduct--},
                    )
                } else {
                    AddToBasketButton (onClick = {showQuantityButtons = true})
                }
            }
        }
    }
}
