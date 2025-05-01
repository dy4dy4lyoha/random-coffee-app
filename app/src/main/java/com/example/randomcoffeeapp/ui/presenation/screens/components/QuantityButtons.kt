package com.example.randomcoffeeapp.ui.presenation.screens.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.theme.openSansFamily

// button "add"
@Composable
fun AddToBasketButton(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onAdd,
        contentPadding = PaddingValues(dimensionResource(R.dimen.small_padding)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primary_button)
        ),
        modifier = Modifier
            .clip(CircleShape)
            .size(dimensionResource(R.dimen.medium_button))
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
        verticalAlignment = Alignment.CenterVertically) {

        // button -1
        Button(
            contentPadding = PaddingValues(dimensionResource(R.dimen.small_padding)),
            onClick = onDecrement,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.neutral_button)
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(dimensionResource(R.dimen.medium_button))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = null,
                tint = colorResource(R.color.neutral_icon),
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.medium_spacer)))

        Text(
            text = count.toString(),
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = openSansFamily,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.medium_spacer)))

        // button +1
        Button(
            onClick = onIncrement,
            contentPadding = PaddingValues(dimensionResource(R.dimen.small_padding)),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.neutral_button)
            ),
            modifier = Modifier
                .clip(CircleShape)
                .size(dimensionResource(R.dimen.medium_button))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = null,
                tint = colorResource(R.color.neutral_icon),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}