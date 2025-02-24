package com.example.warmupexample.application.facade.product

import com.example.warmupexample.domain.product.service.ProductService
import com.example.warmupexample.domain.product.service.toResult
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val productService: ProductService,
) {
    fun getProducts(): List<ProductResult> {
        val products = productService.getProducts()
        return products.map { it.toResult() }
    }
}