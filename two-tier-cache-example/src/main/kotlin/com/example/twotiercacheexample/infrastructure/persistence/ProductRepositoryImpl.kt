package com.example.twotiercacheexample.infrastructure.persistence

import com.example.twotiercacheexample.domain.product.Product
import com.example.twotiercacheexample.domain.product.repository.ProductRepository
import com.example.twotiercacheexample.infrastructure.persistence.jpa.ProductJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    val productJpaRepository: ProductJpaRepository
) : ProductRepository {

    override fun getProducts(): List<Product> {
        return productJpaRepository.findAll()
    }

    override fun getProduct(id: Long): Product? {
        return productJpaRepository.findByIdOrNull(id);
    }

    override fun getProductsByName(name: String): List<Product>? {
        return productJpaRepository.findProductsByName(name);
    }

    override fun create(product: Product) {
        productJpaRepository.save(product)
    }

    override fun saveAll(products: List<Product>) {
        productJpaRepository.saveAll(products)
    }
}