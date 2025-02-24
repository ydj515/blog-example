package com.example.warmupexample.infrastructure.persistence.jpa

import com.example.warmupexample.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>