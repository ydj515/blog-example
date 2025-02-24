package com.example.warmupexample.application.facade.user

import com.example.warmupexample.domain.user.service.UserService
import com.example.warmupexample.domain.user.service.toResult
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService,
) {
    fun getUsers(): List<UserResult> {
        val users = userService.getUsers()
        return users.map { it.toResult() }
    }
}