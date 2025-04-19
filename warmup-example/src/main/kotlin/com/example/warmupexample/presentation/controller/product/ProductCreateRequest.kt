package com.example.warmupexample.presentation.controller.product

import com.example.warmupexample.application.facade.product.ProductCreateCriteria
import java.math.BigDecimal

data class ProductCreateRequest(
    val name: String,
    val price: BigDecimal,
)

fun ProductCreateRequest.toCriteria(): ProductCreateCriteria {
    return ProductCreateCriteria(
        name = this.name,
        price = this.price,
    )
}
