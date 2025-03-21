package com.example.enumbeanexample.service

import com.example.enumbeanexample.enums.PaymentType
import org.springframework.stereotype.Component

@Component
class PaymentProcessor(private val paymentService: PaymentService) {
    fun executePayment(type: PaymentType, amount: Double) {
        paymentService.pay(type, amount)
    }
}