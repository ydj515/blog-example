package com.example.twotiercacheexample.domain.product.service

import com.example.twotiercacheexample.application.facade.product.ProductResult
import java.io.Serializable
import java.math.BigDecimal

data class ProductInfo(
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO
): Serializable


fun ProductInfo.toResult(): ProductResult {
    return ProductResult(
        name = name,
        price = price,
    )
}