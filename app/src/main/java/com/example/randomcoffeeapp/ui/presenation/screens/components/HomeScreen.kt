package com.example.randomcoffeeapp.ui.presenation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.presenation.models.Product
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme


@Composable
fun MainScreenGrid(modifier: Modifier = Modifier) {
}

@Composable
fun MainScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            ProductCard(onClick = {})
            ProductCard(onClick = {})
        }
        Row {
            ProductCard(onClick = {})
            ProductCard(onClick = {})
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
            MainScreen()
        }
    }
}