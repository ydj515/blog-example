package com.example.twotiercacheexample

import com.example.twotiercacheexample.domain.product.Product
import com.example.twotiercacheexample.domain.product.repository.ProductRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class SpringEventHandler(
    private val productRepository: ProductRepository
) {
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent?) {
        val products = listOf(
            Product(name = "A", price = BigDecimal.ONE),
            Product(name = "A", price = BigDecimal.ONE),
            Product(name = "B", price = BigDecimal.ONE),
            Product(name = "B", price = BigDecimal.ONE),
            Product(name = "B", price = BigDecimal.ONE),
            Product(name = "C", price = BigDecimal.ONE),
            Product(name = "D", price = BigDecimal.ONE),
            Product(name = "E", price = BigDecimal.ONE),
        )

        productRepository.saveAll(products)
    }
}