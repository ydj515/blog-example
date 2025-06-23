package com.example.proxyqueryplanexample.repository;

import com.example.proxyqueryplanexample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}