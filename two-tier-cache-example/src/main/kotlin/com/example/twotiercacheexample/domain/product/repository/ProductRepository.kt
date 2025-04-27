package com.example.twotiercacheexample.domain.product.repository

import com.example.twotiercacheexample.domain.product.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun getProduct(id: Long): Product?
    fun getProductsByName(name: String): List<Product>?
    fun create(product: Product)
    fun saveAll(products: List<Product>)
}