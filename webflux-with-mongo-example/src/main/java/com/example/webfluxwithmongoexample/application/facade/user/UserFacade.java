package com.example.webfluxwithmongoexample.application.facade.user;

import com.example.webfluxwithmongoexample.domain.user.service.UserCommand;
import com.example.webfluxwithmongoexample.domain.user.service.UserInfo;
import com.example.webfluxwithmongoexample.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public Mono<UserResult> createUser(UserCriteria criteria) {
        UserCommand userCommand = new UserCommand(criteria);
        Mono<UserInfo> userInfo = userService.save(userCommand);

        return userInfo.map(UserResult::new);
    }
}
