package com.example.proxyqueryplanexample.repository;

import com.example.proxyqueryplanexample.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}