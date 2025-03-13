package com.example.springbasicnewspeed.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserProfileResponse {

    private final String email;
    private final String userName;

    public UserProfileResponse(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }
}
