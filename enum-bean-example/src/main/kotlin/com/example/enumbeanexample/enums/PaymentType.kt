package com.example.enumbeanexample.enums

import com.example.enumbeanexample.service.PaymentService
import com.example.enumbeanexample.util.SpringContextUtil

enum class PaymentType {
    CREDIT_CARD,
    PAYPAL;

    private val paymentService: PaymentService by lazy {
        SpringContextUtil.getBean(PaymentService::class.java)
    }

    fun processPayment(amount: Double) {
        paymentService.pay(this, amount)
    }
}