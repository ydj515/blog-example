package com.example.localcacheexample.domain.product.repository

import com.example.localcacheexample.domain.product.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun getProduct(id: Long): Product?
    fun getProductsByName(name: String): List<Product>?
    fun create(product: Product)
    fun saveAll(products: List<Product>)
}