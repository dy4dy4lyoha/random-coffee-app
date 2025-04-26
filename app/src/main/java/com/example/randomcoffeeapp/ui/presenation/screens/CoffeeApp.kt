package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme

@Composable
fun CoffeeApp() {
    MainScreen()
}

@Composable
@Preview
fun CoffeeAppPreview() {
    RandomCoffeeAppTheme {
        CoffeeApp()
    }
}