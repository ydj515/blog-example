package com.example.globalcacheexample.domain.product.extensions

import com.example.globalcacheexample.domain.product.Product
import com.example.globalcacheexample.domain.product.service.ProductInfo

fun Product.toInfo(): ProductInfo {
    return ProductInfo(
        name = this.name,
        price = this.price
    )
}