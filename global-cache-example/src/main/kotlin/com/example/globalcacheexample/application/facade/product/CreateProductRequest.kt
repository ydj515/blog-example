package com.example.globalcacheexample.application.facade.product

import com.example.globalcacheexample.domain.product.service.CreateProductCommand
import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
)

fun CreateProductRequest.toCommand(): CreateProductCommand {
    return CreateProductCommand(
        name = this.name,
        price = this.price
    )
}