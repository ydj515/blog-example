package com.example.enumbeanexample.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PaymentTypeTest {


    @Test
    fun `CREDIT_CARD 결제 시 PaymentService가 호출되는지 확인`() {
        PaymentType.CREDIT_CARD.processPayment(1.0)
    }

    @Test
    fun `PAYPAL 결제 시 PaymentService가 호출되는지 확인`() {
        PaymentType.PAYPAL.processPayment(20.0)
    }

}