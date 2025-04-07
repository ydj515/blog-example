package com.example.globalcacheexample.domain.product.service

import com.example.globalcacheexample.domain.product.Product
import com.example.globalcacheexample.domain.product.extensions.toInfo
import com.example.globalcacheexample.domain.product.repository.ProductRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    @Cacheable(cacheNames = ["productList"])
    fun getProducts(): List<ProductInfo> {
        println("db 조회 getProducts")
        val products = productRepository.getProducts()
        return products.map { it.toInfo() }
    }


    @Cacheable(cacheNames = ["product"], key = "#id")
    fun getProduct(id: Long): ProductInfo {
        println("db 조회 getProduct")
        val product = productRepository.getProduct(id)
            ?: throw IllegalArgumentException("Product with id $id not found")
        return product.toInfo()
    }


    @Cacheable(cacheNames = ["productByName"], key = "#name")
    fun getProductsByName(name: String): List<ProductInfo> {
        println("db 조회 getProductsByName")
        val products = productRepository.getProductsByName(name).orEmpty()
        return products.map { it.toInfo() }
    }

    fun createProduct(command: CreateProductCommand) {
        val product = Product(
            name = command.name,
            price = command.price,
        )
        productRepository.create(product)
    }

    fun saveAllProducts(commands: List<CreateProductCommand>) {
        val products = commands.map {
            Product(
                name = it.name,
                price = it.price,
            )
        }
        productRepository.saveAll(products)
    }
}