package com.example.warmupexample.application.facade.user

import com.example.warmupexample.presentation.controller.user.UserResponse

class UserResult(
    val username: String,
)

fun UserResult.toResponse(): UserResponse {
    return UserResponse(
        username = this.username,
    )
}