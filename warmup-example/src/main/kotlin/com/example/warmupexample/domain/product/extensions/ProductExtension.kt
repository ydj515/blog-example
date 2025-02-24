package com.example.warmupexample.domain.product.extensions

import com.example.warmupexample.domain.product.Product
import com.example.warmupexample.domain.product.service.ProductInfo

fun Product.toInfo(): ProductInfo {
    return ProductInfo(
        name = this.name,
        price = this.price
    )
}