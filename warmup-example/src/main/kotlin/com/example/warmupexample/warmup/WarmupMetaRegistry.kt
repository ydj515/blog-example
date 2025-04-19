package com.example.warmupexample.warmup

import com.example.warmupexample.presentation.controller.product.ProductCreateRequest
import java.math.BigDecimal

// 파라미터가 있는 메소드 warmup을 위한 registry
object WarmupMetaRegistry {
    private val metaMap: Map<String, Array<Any>> = mapOf(
        "createProduct" to arrayOf(
            ProductCreateRequest("테스트상품", BigDecimal("1000"))
        )
    )

    fun getArgs(metaKey: String): Array<Any>? = metaMap[metaKey]
}