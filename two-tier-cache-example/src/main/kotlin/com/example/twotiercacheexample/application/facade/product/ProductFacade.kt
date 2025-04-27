package com.example.twotiercacheexample.application.facade.product

import com.example.twotiercacheexample.domain.product.service.ProductService
import com.example.twotiercacheexample.domain.product.service.toResult
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val productService: ProductService,
) {
    fun getProducts(): List<ProductResult> {
        val products = productService.getProducts()
        return products.map { it.toResult() }
    }

    fun getProduct(id: Long): ProductResult {
        val product = productService.getProduct(id)
        return product.toResult()
    }

    fun getProductsByName(name: String): List<ProductResult> {
        return productService.getProductsByName(name)
            .map { it.toResult() }
    }

    fun createProduct(request: CreateProductRequest) {
        val command = request.toCommand()
        productService.createProduct(command)
    }

    fun saveAllProducts(requests: List<CreateProductRequest>) {
        val commands = requests.map { it.toCommand() }
        productService.saveAllProducts(commands)
    }
}