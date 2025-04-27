package com.example.twotiercacheexample.domain.product.service

import java.math.BigDecimal

data class CreateProductCommand(
    val name: String,
    val price: BigDecimal,
)