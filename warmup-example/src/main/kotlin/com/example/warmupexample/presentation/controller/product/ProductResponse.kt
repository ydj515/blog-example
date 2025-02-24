package com.example.warmupexample.presentation.controller.product

import java.math.BigDecimal

data class ProductResponse(
    val name: String,
    val price: BigDecimal,
)