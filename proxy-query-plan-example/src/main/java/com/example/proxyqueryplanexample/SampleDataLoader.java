package com.example.proxyqueryplanexample;

import com.example.proxyqueryplanexample.domain.Post;
import com.example.proxyqueryplanexample.domain.User;
import com.example.proxyqueryplanexample.repository.PostRepository;
import com.example.proxyqueryplanexample.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataLoader {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PostRepository postRepository) {
        return args -> {
            for (int i = 1; i <= 5; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user = userRepository.save(user);

                for (int j = 1; j <= 100; j++) {
                    Post post = new Post();
                    post.setTitle("Post " + j + " by " + user.getUsername());
                    post.setUser(user);
                    postRepository.save(post);
                }
            }
        };
    }
}
