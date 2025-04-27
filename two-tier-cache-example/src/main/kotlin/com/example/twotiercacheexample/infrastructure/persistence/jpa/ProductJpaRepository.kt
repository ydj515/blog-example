package com.example.twotiercacheexample.infrastructure.persistence.jpa

import com.example.twotiercacheexample.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long> {
    fun findProductsByName(name: String): MutableList<Product>
}