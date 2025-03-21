package com.example.enumbeanexample.service

import com.example.enumbeanexample.enums.PaymentType
import org.springframework.stereotype.Service

@Service
class PaymentService {

    fun pay(paymentType: PaymentType, amount: Double) {
        println("Processing $amount payment via $paymentType")
    }
}