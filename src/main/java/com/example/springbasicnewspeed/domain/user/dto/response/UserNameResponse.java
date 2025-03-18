package com.example.springbasicnewspeed.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserNameResponse {

    private final String userName;

    public UserNameResponse(String userName) {
        this.userName = userName;
    }
}
