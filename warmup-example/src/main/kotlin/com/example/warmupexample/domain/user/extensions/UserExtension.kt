package com.example.warmupexample.domain.user.extensions

import com.example.warmupexample.domain.user.User
import com.example.warmupexample.domain.user.service.UserInfo

fun User.toInfo(): UserInfo = UserInfo(
    username = username,
)