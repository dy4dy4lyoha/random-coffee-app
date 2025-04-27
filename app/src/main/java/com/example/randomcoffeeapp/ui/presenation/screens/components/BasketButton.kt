package com.example.randomcoffeeapp.ui.presenation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme

// button "add"
@Composable
fun AddToBasketButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF269DD1)
        ),
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

// buttons "+1" and "-1"
@Composable
fun QuantityButtons(count: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        // button -1
        Button(
            contentPadding = PaddingValues(10.dp),
            onClick = onDecrement,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD9D9D9)
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = null,
                tint = Color(0xFF484647),
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = count.toString(),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.width(20.dp))

        // button +1
        Button(
            onClick = onIncrement,
            contentPadding = PaddingValues(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD9D9D9)
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
                tint = Color(0xFF484647),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
@Preview
fun BasketButtonPreview() {
    RandomCoffeeAppTheme {
        QuantityButtons(1, {}, {})
    }
}
