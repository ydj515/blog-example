package com.example.twotiercacheexample.application.facade.product

import com.example.twotiercacheexample.domain.product.service.CreateProductCommand
import java.math.BigDecimal

data class CreateProductRequest(
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO
)

fun CreateProductRequest.toCommand(): CreateProductCommand {
    return CreateProductCommand(
        name = this.name,
        price = this.price
    )
}