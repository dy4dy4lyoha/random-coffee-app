package com.example.randomcoffeeapp.ui.presenation.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomcoffeeapp.network.responses.Category

@Composable
fun PaginationBar(
    currentPage: Int,
    totalPages: Int,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // previous button
        if (currentPage > 0) {
            Button(
                onClick = { onPageChanged(currentPage - 1)}
            ) {
                Text("previous")
            }
        }
        Text ("Page ${currentPage+1} of $totalPages")
    }
}