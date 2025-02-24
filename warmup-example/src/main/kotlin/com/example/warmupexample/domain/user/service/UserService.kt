package com.example.warmupexample.domain.user.service

import com.example.warmupexample.domain.user.extensions.toInfo
import com.example.warmupexample.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUsers(): List<UserInfo> {
        val users = userRepository.getUsers()
        return users.map { user -> user.toInfo() }
    }
}