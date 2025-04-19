package com.example.warmupexample.application.facade.product

import java.math.BigDecimal

data class ProductCreateCriteria(
    val name: String,
    val price: BigDecimal,
)

fun ProductCreateCriteria.toCommand(): ProductCreateCommand {
    return ProductCreateCommand(
        name = this.name,
        price = this.price,
    )
}