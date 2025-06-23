package com.example.proxyqueryplanexample.controller;

import com.example.proxyqueryplanexample.domain.Post;
import com.example.proxyqueryplanexample.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postService.findAllPostsWithUser();
    }
}
