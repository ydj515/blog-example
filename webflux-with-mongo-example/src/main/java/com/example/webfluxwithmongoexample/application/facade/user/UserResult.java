package com.example.webfluxwithmongoexample.application.facade.user;

import com.example.webfluxwithmongoexample.domain.user.service.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResult {
    private String name;

    public UserResult(UserInfo userInfo) {
        this.name = userInfo.getName();
    }
}
