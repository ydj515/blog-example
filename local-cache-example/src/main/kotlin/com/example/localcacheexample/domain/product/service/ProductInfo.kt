package com.example.localcacheexample.domain.product.service

import com.example.localcacheexample.application.facade.product.ProductResult
import java.math.BigDecimal

data class ProductInfo(
    val name: String,
    val price: BigDecimal,
)

fun ProductInfo.toResult(): ProductResult {
    return ProductResult(
        name = name,
        price = price,
    )
}