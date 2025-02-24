package com.example.warmupexample.presentation.controller.user

import com.example.warmupexample.application.facade.user.UserFacade
import com.example.warmupexample.application.facade.user.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userFacade: UserFacade,
) {

    @GetMapping("")
    fun getUsers(): ResponseEntity<List<UserResponse>> {
        val results = userFacade.getUsers()
        val response = results.map { user -> user.toResponse() }
        return ResponseEntity(response, HttpStatus.OK)
    }
}