package com.example.warmupexample.presentation.controller.product

import com.example.warmupexample.application.facade.product.ProductFacade
import com.example.warmupexample.application.facade.product.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productFacade: ProductFacade
) {
    @GetMapping("")
    fun getProducts(): ResponseEntity<List<ProductResponse>> {
        val result = productFacade.getProducts()
        val response = result.map { it.toResponse() }
        return ResponseEntity(response, HttpStatus.OK)
    }
}