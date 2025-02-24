package com.example.warmupexample.domain.user.service

import com.example.warmupexample.application.facade.user.UserResult

data class UserInfo(
    val username: String,
)

fun UserInfo.toResult(): UserResult {
    return UserResult(
        username = username,
    )
}