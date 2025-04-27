package com.example.twotiercacheexample.presentation.product

import com.example.twotiercacheexample.application.facade.product.CreateProductRequest
import com.example.twotiercacheexample.application.facade.product.ProductFacade
import com.example.twotiercacheexample.application.facade.product.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productFacade: ProductFacade
) {
    @GetMapping("")
    fun getProducts(): ResponseEntity<List<ProductResponse>> {
        val startTime = System.nanoTime()
        val result = productFacade.getProducts()
        val response = result.map { it.toResponse() }

        val endTime = System.nanoTime()
        val elapsedTime = (endTime - startTime) / 1_000_000.0

        println("실행시간: %.3f ms".format(elapsedTime))

        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        val result = productFacade.getProduct(id)
        val response = result.toResponse()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun getProductsByName(@RequestParam name: String): ResponseEntity<List<ProductResponse>> {
        val result = productFacade.getProductsByName(name)
        val response = result.map { it.toResponse() }
        return ResponseEntity.ok(response)
    }

    @PostMapping("")
    fun createProduct(@RequestBody request: CreateProductRequest): ResponseEntity<Void> {
        productFacade.createProduct(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/bulk")
    fun saveAllProducts(@RequestBody requests: List<CreateProductRequest>): ResponseEntity<Void> {
        productFacade.saveAllProducts(requests)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}