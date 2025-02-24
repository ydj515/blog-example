package com.example.warmupexample.infrastructure.persistence

import com.example.warmupexample.domain.product.Product
import com.example.warmupexample.domain.product.repository.ProductRepository
import com.example.warmupexample.infrastructure.persistence.jpa.ProductJpaRepository
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    val productJpaRepository: ProductJpaRepository
) : ProductRepository {

    override fun getProducts(): List<Product> {
        return productJpaRepository.findAll()
    }
}