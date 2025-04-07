package com.example.localcacheexample.domain.product.extensions

import com.example.localcacheexample.domain.product.Product
import com.example.localcacheexample.domain.product.service.ProductInfo

fun Product.toInfo(): ProductInfo {
    return ProductInfo(
        name = this.name,
        price = this.price
    )
}