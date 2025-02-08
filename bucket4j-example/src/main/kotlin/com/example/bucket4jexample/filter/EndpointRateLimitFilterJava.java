package com.example.bucket4jexample.filter;

import com.example.bucket4jexample.helper.RateLimiterJava;
import com.example.bucket4jexample.util.IpUtilsJava;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class EndpointRateLimitFilterJava extends OncePerRequestFilter {

    private final RateLimiterJava rateLimiter;

    public EndpointRateLimitFilterJava(RateLimiterJava rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = IpUtilsJava.getClientIp(request);
        String path = request.getRequestURI();
        String key = ip + ":" + path;

        if (!rateLimiter.isRequestAllowed(key, path)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests. Try again later.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
