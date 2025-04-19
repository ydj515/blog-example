package com.example.warmupexample.application.facade.product

import java.math.BigDecimal

class ProductCreateCommand(
    val name: String,
    val price: BigDecimal,
)