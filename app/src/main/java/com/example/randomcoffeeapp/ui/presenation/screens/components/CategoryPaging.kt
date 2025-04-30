//package com.example.randomcoffeeapp.ui.presenation.screens.components
//
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.randomcoffeeapp.network.responses.Category
//
//@Composable
//fun CategoryPaging (
//    categories: List<Category>,
//    selectedCategory: Category?,
//    onCategorySelected: (Category?) -> Unit,
//) {
//    LazyHorizontalGrid(
//        rows = GridCells.Fixed(1),
//        modifier = Modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(horizontal = 16.dp)
//    ) {
//        items(categories, key = { it.id }) { category ->
//            TextButton(
//                onClick = {onCategorySelected(category) },
//            ) {
//                Text(text = category.slug)
//            }
//        }
//    }
//}