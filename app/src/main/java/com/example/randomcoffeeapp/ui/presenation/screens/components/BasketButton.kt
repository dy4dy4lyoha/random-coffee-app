package com.example.randomcoffeeapp.ui.presenation.screens.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketButton() {

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    Button(onClick = {
            scope.launch {
                if (sheetState.isVisible) {
                    sheetState.hide()
                } else {
                    sheetState.show()
                }
            }
        },
        contentPadding = PaddingValues(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF269DD1)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .size(width = 105.dp, height = 60.dp)
    ) {
        Row (
             verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = null,
            )
            Text(
                text = "450 $"
            )
        }
    }
}

@Composable
@Preview
fun BasketButtonPreview() {
    RandomCoffeeAppTheme {
        BasketButton()
    }
}