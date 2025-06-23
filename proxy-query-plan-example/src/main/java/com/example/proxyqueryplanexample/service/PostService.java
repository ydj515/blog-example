package com.example.proxyqueryplanexample.service;

import com.example.proxyqueryplanexample.domain.Post;
import com.example.proxyqueryplanexample.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPostsWithUser() {
        // 일부러 느린 쿼리를 만들기 위해 전체 posts + users fetch
        return postRepository.findAll();
    }
}
