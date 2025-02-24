package com.example.warmupexample.infrastructure.persistence.jpa

import com.example.warmupexample.domain.product.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<Product, Long>