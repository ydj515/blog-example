package com.example.twotiercacheexample.domain.product.extensions

import com.example.twotiercacheexample.domain.product.Product
import com.example.twotiercacheexample.domain.product.service.ProductInfo

fun Product.toInfo(): ProductInfo {
    return ProductInfo(
        name = this.name,
        price = this.price
    )
}