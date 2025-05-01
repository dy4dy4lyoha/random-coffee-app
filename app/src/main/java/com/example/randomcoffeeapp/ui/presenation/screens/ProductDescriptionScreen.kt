package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.network.responses.Product
import com.example.randomcoffeeapp.ui.theme.openSansFamily

@Composable
fun ProductDescriptionScreen(
    product: Product,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = dimensionResource(R.dimen.large_padding)
            )
    )
    {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.Start)
        ) {
            Icon (
                painter = painterResource(R.drawable.ic_back_to_previous),
                tint = colorResource(R.color.neutral_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.small_icon))
            )
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
                    .size(dimensionResource(R.dimen.large_image))
            )
        }
        // title
        Text(
            text = product.name,
            fontSize = 40.sp,
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.medium_padding),
                end = dimensionResource(R.dimen.medium_padding)
            )
        )
        // description
        Text(
            text = product.description,
            fontSize = 16.sp,
            fontFamily = openSansFamily,
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.medium_padding),
                end = dimensionResource(R.dimen.medium_padding)
            )
        )
    }
}
