package com.example.warmupexample

import com.example.warmupexample.presentation.controller.product.ProductController
import com.example.warmupexample.presentation.controller.user.UserController
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SpringEventHandler(
    private val productController: ProductController,
    private val userController: UserController,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent(event: ApplicationReadyEvent?) {
        println("ApplicationReadyEvent!")
        println("warm up")
        userController.getUsers()
        productController.getProducts()
    }
}

