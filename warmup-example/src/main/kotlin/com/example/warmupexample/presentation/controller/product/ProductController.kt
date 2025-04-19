package com.example.warmupexample.presentation.controller.product

import com.example.warmupexample.application.facade.product.ProductFacade
import com.example.warmupexample.application.facade.product.toResponse
import com.example.warmupexample.warmup.Warmup
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productFacade: ProductFacade
) {
    @GetMapping("")
    @Warmup
    fun getProducts(): ResponseEntity<List<ProductResponse>> {
        val startTime = System.nanoTime()
        val result = productFacade.getProducts()
        val response = result.map { it.toResponse() }

        val endTime = System.nanoTime()
        val elapsedTime = (endTime - startTime) / 1_000_000.0

        println("실행시간: %.3f ms".format(elapsedTime))

        return ResponseEntity(response, HttpStatus.OK)
    }

    @PostMapping
    @Warmup(metaKey = "createProduct")
    fun createProduct(@RequestBody request: ProductCreateRequest): ResponseEntity<ProductResponse> {
        val startTime = System.nanoTime()
        val criteria = request.toCriteria()
        val result = productFacade.create(criteria)
        val response = result.toResponse()

        val endTime = System.nanoTime()
        val elapsedTime = (endTime - startTime) / 1_000_000.0

        println("실행시간: %.3f ms".format(elapsedTime))
        return ResponseEntity(response, HttpStatus.CREATED)
    }
}