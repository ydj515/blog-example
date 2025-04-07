package com.example.globalcacheexample.infrastructure.persistence.jpa

import com.example.globalcacheexample.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
    fun findProductsByName(name: String): MutableList<Product>
}