package com.example.twotiercacheexample.domain.product.service

import com.example.twotiercacheexample.domain.cache.HybridCacheService
import com.example.twotiercacheexample.domain.product.Product
import com.example.twotiercacheexample.domain.product.extensions.toInfo
import com.example.twotiercacheexample.domain.product.repository.ProductRepository
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val hybridCacheService: HybridCacheService,
) {

    fun getProducts(): List<ProductInfo> {
        return hybridCacheService.get(
            key = "products",
            typeReference = object : TypeReference<List<ProductInfo>>() {},
        ) {
            println("DB 조회 - getProducts")
            val products = productRepository.getProducts()
            products.map { it.toInfo() }
        }

    }

    fun getProduct(id: Long): ProductInfo {
        val cacheKey = "product:$id"
        return hybridCacheService.get(
            cacheKey,
            typeReference = object : TypeReference<ProductInfo>() {},
        ) {
            println("DB 조회 - getProduct($id)")
            val product = productRepository.getProduct(id)
                ?: throw IllegalArgumentException("Product with id $id not found")
            product.toInfo()
        }
    }

    fun getProductsByName(name: String): List<ProductInfo> {
        val cacheKey = "products:name:$name"
        return hybridCacheService.get(cacheKey, typeReference = object : TypeReference<List<ProductInfo>>() {}) {
            println("DB 조회 - getProductsByName($name)")
            val products = productRepository.getProductsByName(name).orEmpty()
            products.map { it.toInfo() }
        }
    }

    fun createProduct(command: CreateProductCommand) {
        val product = Product(
            name = command.name,
            price = command.price,
        )
        productRepository.create(product)

        // 데이터가 바뀌었으면 캐시 날리기 (간단히 전체 무효화)
        hybridCacheService.evict("products")
        hybridCacheService.evict("products:name:${command.name}")
    }

    fun saveAllProducts(commands: List<CreateProductCommand>) {
        val products = commands.map {
            Product(
                name = it.name,
                price = it.price,
            )
        }
        productRepository.saveAll(products)

        // 한번에 등록했으면 전체 무효화
        hybridCacheService.evict("products")
    }
}