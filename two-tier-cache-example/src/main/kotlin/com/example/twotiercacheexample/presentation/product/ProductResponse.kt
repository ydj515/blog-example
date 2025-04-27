package com.example.twotiercacheexample.presentation.product

import java.math.BigDecimal

data class ProductResponse(
    val name: String,
    val price: BigDecimal,
)