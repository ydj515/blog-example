package com.example.warmupexample.presentation.controller.user

import com.example.warmupexample.application.facade.user.UserFacade
import com.example.warmupexample.application.facade.user.toResponse
import com.example.warmupexample.warmup.Warmup
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Warmup
class UserController(
    private val userFacade: UserFacade,
) {

    @GetMapping("")
    fun getUsers(): ResponseEntity<List<UserResponse>> {
        val startTime = System.nanoTime()
        val results = userFacade.getUsers()
        val response = results.map { user -> user.toResponse() }

        val endTime = System.nanoTime()
        val elapsedTime = (endTime - startTime) / 1_000_000.0

        println("실행시간: %.3f ms".format(elapsedTime))

        return ResponseEntity(response, HttpStatus.OK)
    }
}