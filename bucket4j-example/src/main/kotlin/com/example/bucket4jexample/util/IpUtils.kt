package com.example.bucket4jexample.util

import jakarta.servlet.http.HttpServletRequest

object IpUtils {
    fun getClientIp(request: HttpServletRequest): String {
        return request.getHeader("X-Forwarded-For")
            ?.takeIf { it.isNotEmpty() }
            ?.split(",")
            ?.firstOrNull()
            ?.trim()
            ?: request.remoteAddr
    }
}