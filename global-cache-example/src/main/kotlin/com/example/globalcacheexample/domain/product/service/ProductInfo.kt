package com.example.globalcacheexample.domain.product.service

import com.example.globalcacheexample.application.facade.product.ProductResult
import java.math.BigDecimal

data class ProductInfo(
    val name: String = "",
    val price: BigDecimal = BigDecimal(0),
)

fun ProductInfo.toResult(): ProductResult {
    return ProductResult(
        name = name,
        price = price,
    )
}