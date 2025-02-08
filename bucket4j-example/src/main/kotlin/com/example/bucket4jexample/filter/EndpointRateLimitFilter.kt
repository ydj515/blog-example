package com.example.bucket4jexample.filter

import com.example.bucket4jexample.helper.RateLimiter
import com.example.bucket4jexample.util.IpUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class EndpointRateLimitFilter(
    private val rateLimiter: RateLimiter
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {
        val ip = IpUtils.getClientIp(request)
        val path = request.requestURI
        val key = "$ip:$path"

        if (!rateLimiter.isRequestAllowed(key, path)) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.writer.write("Too many requests. Try again later.")
            return
        }

        filterChain.doFilter(request, response)
    }
}