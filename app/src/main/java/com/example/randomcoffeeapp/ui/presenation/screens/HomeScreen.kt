package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomcoffeeapp.ui.presenation.screens.components.ProductCard
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme

@Composable
fun MainScreen(
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            ProductCard(onClick = {}, id = 200)
            ProductCard(onClick = {}, id = 4)
        }
        Row {
            ProductCard(onClick = {}, id = 5)
            ProductCard(onClick = {}, id = 200)
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