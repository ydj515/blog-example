package com.example.warmupexample.infrastructure.persistence

import com.example.warmupexample.domain.user.User
import com.example.warmupexample.domain.user.repository.UserRepository
import com.example.warmupexample.infrastructure.persistence.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {

    override fun getUsers(): List<User> {
        return userJpaRepository.findAll()
    }
}