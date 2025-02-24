package com.example.warmupexample.domain.user.repository

import com.example.warmupexample.domain.user.User

interface UserRepository {
    fun getUsers(): List<User>
}