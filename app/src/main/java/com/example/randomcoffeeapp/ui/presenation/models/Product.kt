package com.example.randomcoffeeapp.ui.presenation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Product (
    @StringRes val stringResourceId: Int,
    @DrawableRes val drawableResourceId: Int,
)