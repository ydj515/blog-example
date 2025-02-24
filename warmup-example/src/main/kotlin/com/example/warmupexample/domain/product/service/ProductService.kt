package com.example.warmupexample.domain.product.service

import com.example.warmupexample.domain.product.Product
import com.example.warmupexample.domain.product.extensions.toInfo
import com.example.warmupexample.domain.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun getProducts(): List<ProductInfo> {
        val products = productRepository.getProducts()
        return products.map { it.toInfo() }
    }

}