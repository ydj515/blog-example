package com.example.localcacheexample.presentation.product

import java.math.BigDecimal

data class ProductResponse(
    val name: String,
    val price: BigDecimal,
)