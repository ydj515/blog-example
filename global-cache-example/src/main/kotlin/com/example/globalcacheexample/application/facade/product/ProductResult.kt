package com.example.globalcacheexample.application.facade.product

import com.example.globalcacheexample.presentation.product.ProductResponse
import java.math.BigDecimal

data class ProductResult(
    val name: String,
    val price: BigDecimal,
)

fun ProductResult.toResponse(): ProductResponse {
    return ProductResponse(
        name = this.name,
        price = this.price,
    )
}