package com.example.randomcoffeeapp.ui.presenation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomcoffeeapp.R
import com.example.randomcoffeeapp.ui.theme.RandomCoffeeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketBottomSheet() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column {
                Row {
                    Text(text = "zakazik")
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_all),
                        contentDescription = null,
                    )
                }
                Divider()
            }
        }
    ) {
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true,)
fun BasketScreenPreview() {
    RandomCoffeeAppTheme {
        BasketBottomSheet()
    }
}