package com.example.webfluxwithmongoexample.domain.user.service;

import com.example.webfluxwithmongoexample.domain.user.repository.UserRepository;
import com.example.webfluxwithmongoexample.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Mono<UserInfo> save(UserCommand command) {
        User user = new User(command);
        Mono<User> savedUser = userRepository.save(user);
        return savedUser.map(saved -> new UserInfo(saved.getName()));
    }
}
