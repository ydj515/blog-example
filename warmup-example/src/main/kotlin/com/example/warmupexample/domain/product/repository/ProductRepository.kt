package com.example.warmupexample.domain.product.repository

import com.example.warmupexample.domain.product.Product

interface ProductRepository {
    fun getProducts(): List<Product>
}