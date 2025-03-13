package com.example.springbasicnewspeed.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String password;
    private final String userName;

    public UserResponse(Long id, String email, String userName,String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
}
